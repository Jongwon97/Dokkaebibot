package com.dokkaebi.service;

import com.dokkaebi.domain.StudyDataInputDTO;
import com.dokkaebi.domain.StudyDataInputDTO.AtmosphereData;
import com.dokkaebi.domain.StudyDataInputDTO.CameraData;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokkaebi.domain.StudyData;
import com.dokkaebi.domain.StudyDataDTO;
import com.dokkaebi.domain.ZandiDto;
import com.dokkaebi.repository.StudyDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StudyDataService {

	private final StudyDataRepository studyDataRepository;

	public StudyDataDTO getDataRange(String start, String end, Long memberId) throws ParseException, NoSuchElementException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("M월d일h시m분");
		Date startDate = inputFormat.parse(start);
		Date endDate = inputFormat.parse(end);

		List<StudyData> studyDataList = studyDataRepository.findDataFromTo(startDate, endDate, memberId);
		if (studyDataList.isEmpty()) {
			throw new NoSuchElementException();
		}

		StudyDataDTO studyDataDTO = new StudyDataDTO();

		studyDataDTO.setStart(studyDataList.get(0).getStart().getTime());
		studyDataDTO.setEnd(studyDataList.get(studyDataList.size() - 1).getEnd().getTime());

		long diff;

		for (int i = 0; i < studyDataList.size(); i++) {
			Gson gson = new Gson();
			StudyDataInputDTO data = gson.fromJson(studyDataList.get(i).getData(), StudyDataInputDTO.class);
			List<AtmosphereData> atmosphereDataList = data.getAtmosphere();
			List<CameraData> cameraDataList = data.getCamera();

			// atmosphereDataList.forEach((atmosphereData -> {
			// 	studyDataDTO.getTimeIndex().add(outputFormat.format(new Date(atmosphereData.getTime())));
			// 	studyDataDTO.getTemperatureData().add(atmosphereData.getTemperature());
			// 	studyDataDTO.getDustData().add(atmosphereData.getDust());
			// 	studyDataDTO.getHumidityData().add(atmosphereData.getHumidity());
			// }));

			

			atmosphereDataList.forEach((atmosphereData -> {
				studyDataDTO.getTimeIndex().add(
								new Date(atmosphereData.getTime()).toInstant()
										.atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("M월d일h시m분s초"))
				);
				studyDataDTO.getTemperatureData().add(atmosphereData.getTemperature());
				studyDataDTO.getDustData().add(atmosphereData.getDust());
				studyDataDTO.getHumidityData().add(atmosphereData.getHumidity());
			}));

			long curTime;
			String curStatus;
			for (int j = 0; j < cameraDataList.size(); j++) {
				curTime = cameraDataList.get(j).getTime();
				curStatus = cameraDataList.get(j).getStatus();
				studyDataDTO.getPoseTimeIndex().add(new Date(curTime).getTime());
				studyDataDTO.getPoseData().add(curStatus);
				if (j != cameraDataList.size() - 1) {
					diff = cameraDataList.get(j + 1).getTime() - curTime;
				} else {
					diff = data.getEnd() - curTime;
				}
				studyDataDTO.getPoseTimeSum().replace(curStatus, studyDataDTO.getPoseTimeSum().get(curStatus) + diff);

				if (j == cameraDataList.size() - 1) { // mark end of the study
					studyDataDTO.getPoseTimeIndex().add(new Date(data.getEnd()).getTime());
					studyDataDTO.getPoseData().add("end");
				}
			}
		}
		return studyDataDTO;
	}

	// zandi 데이터 반환 메서드
	public List<ZandiDto> getZandiDto(Long memberId) {
		List<ZandiDto> zandiList = new ArrayList<>();

		Year currentYear = Year.now(); // 현재 년도
		Month currentMonth = Month.from(java.time.LocalDate.now()); // 현재 월

		// 마지막 일자 계산
		LocalDate lastDayOfMonth = LocalDate.of(currentYear.getValue(), currentMonth.getValue(), 1)
				.with(TemporalAdjusters.lastDayOfMonth());
		int endDay = lastDayOfMonth.getDayOfMonth(); // 마지막 날짜

		// 해당 월에 날짜만큼 반복
		for (int i = 1; i <=endDay; i++) {
			int day = i;

			LocalDate start = LocalDate.of(currentYear.getValue(), currentMonth.getValue(), day);
			Date utilStart = java.sql.Date.valueOf(start);

			LocalDate end = null;
			Date utilEnd =	null;

			// 월 마지막 날이 아닌 경우
			if(i!=endDay) {
				end = LocalDate.of(currentYear.getValue(), currentMonth.getValue(), day + 1);
				utilEnd = java.sql.Date.valueOf(end);
			}
			// 월 마지막 날인 경우
			else {
				end = LocalDate.of(currentYear.getValue(), currentMonth.getValue()+1, 1);
				utilEnd = java.sql.Date.valueOf(end);
			}
			
			// 날짜별 공부 데이터 조회
			List<StudyData> studyDataList = studyDataRepository.findDataFromTo(utilStart, utilEnd, memberId);

			// 해당 날짜에 공부 안한 경우
			if (studyDataList.size() == 0) {
				String currentMonthValue = String.format("%02d", currentMonth.getValue());
				String dayValue = String.format("%02d", i);
				String date = currentYear + "-" + currentMonthValue + "-" + dayValue;
				zandiList.add(new ZandiDto(i, date, "00:00:00"));
			}
			// 해당 날짜에 공부 한 경우
			else {
				int totalSecond = 0;
				SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
				for (StudyData data : studyDataList) {
					Date startDate = data.getStart();
					Date endDate = data.getEnd();
					// 시작 날짜와 끝나는 날짜가 다른 경우(밤에 시작해서 새벽까지 하는 경우 고려)
					if (!dateFormatDay.format(startDate).equals(dateFormatDay.format(endDate))) {
						totalSecond += 86400 - convertTimeToSecond(startDate) + convertTimeToSecond(endDate);
						continue;
					}
					totalSecond += convertTimeToSecond(endDate) - convertTimeToSecond(startDate); // 공부한 시간(끝난 시간 - 시작
																									// 시간 (초 단위) )
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(studyDataList.get(0).getStart()); // 맨 처음 시간 설정

				Date date = convertSecondToDate(calendar, totalSecond); // 공부한 날짜 시간 반환

				SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:mm:ss");
				String dayValue = dateFormatDay.format(date);
				String hourValue = dateFormatHour.format(date);
				zandiList.add(new ZandiDto(i, dayValue, hourValue));
			}
		}
		return zandiList;
	}

	// Date를 받아서 초로 변환해주는 메서드
	public int convertTimeToSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		return (hours * 3600) + (minutes * 60) + seconds;
	}

	// 전체 초, 캘린더를 매개변수로 받아서 date 타입으로 변환해주는 메서드
	public Date convertSecondToDate(Calendar cal, int totalSeconds) {
		// 초를 시간, 분, 초로 나눔
		int hours = totalSeconds / 3600;
		int remainingSeconds = totalSeconds % 3600;
		int minutes = remainingSeconds / 60; // 나머지 초를 분으로 나눈 몫
		int seconds = remainingSeconds % 60; // 분을 뺀 나머지 초
		// 시간 변경
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		return cal.getTime();
	}
}
