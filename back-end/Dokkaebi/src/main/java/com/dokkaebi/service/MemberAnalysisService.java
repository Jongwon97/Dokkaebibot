package com.dokkaebi.service;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberAnalysis;
import com.dokkaebi.domain.RankerDTO;
import com.dokkaebi.domain.StudyDataInputDTO;
import com.dokkaebi.domain.StudyDataInputDTO.CameraData;
import com.dokkaebi.repository.MemberAnalysisRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberAnalysisService {
  private final MemberAnalysisRepository memberAnalysisRepository;
  public void analyzeAndSave(Member member, StudyDataInputDTO data) {
    Optional<MemberAnalysis> optionalMemberAnalysis = memberAnalysisRepository.findByMember(member);
    MemberAnalysis memberAnalysis = optionalMemberAnalysis.orElse(new MemberAnalysis(member));

    // timeTotalStudy
    memberAnalysis.setTimeTotalStudy(memberAnalysis.getTimeTotalStudy() + data.getEnd() - data.getStart());
    // timeNetStudy & timeGoodPose
    List<CameraData> cameraData = data.getCamera();
    String status;
    Long diff;
    Long beforeNet = memberAnalysis.getTimeNetStudy();
    Long beforeGood = memberAnalysis.getTimeGoodPose();
    for (int i = 0; i < cameraData.size(); i++) {
      status = String.valueOf(cameraData.get(i).getStatus());
      if (i != cameraData.size() - 1) {
        diff = cameraData.get(i + 1).getTime() - cameraData.get(i).getTime();
      } else {
        diff = data.getEnd() - cameraData.get(i).getTime();
      }
      if (status.equals("bad")) {
        beforeNet += diff;
      } else if (status.equals("good")) {
        beforeNet += diff;
        beforeGood += diff;
      }
    }
    memberAnalysis.setTimeNetStudy(beforeNet);
    memberAnalysis.setTimeGoodPose(beforeGood);
    memberAnalysisRepository.save(memberAnalysis);
  }


  private static String toTimeString(Long timestamp) {
    String hours = "0" + timestamp / 1000 / 60 / 60 % 60;
    String minutes = "0" + timestamp / 1000 / 60 % 60;
    String seconds = "0" + timestamp / 1000 % 60;
    return hours.substring(hours.length() - 2)
        + ":" + minutes.substring(minutes.length() - 2)
        + ":" + seconds.substring(seconds.length() - 2);
  }

  public RankerDTO getTotalRanker() throws NoSuchElementException {
      MemberAnalysis memberAnalysis = memberAnalysisRepository.getTotalRanker();
      return new RankerDTO(memberAnalysis.getMember(), toTimeString(memberAnalysis.getTimeTotalStudy()));
  }

  public RankerDTO getNetRanker() throws NoSuchElementException {
    MemberAnalysis memberAnalysis = memberAnalysisRepository.getNetRanker();
    return new RankerDTO(memberAnalysis.getMember(), toTimeString(memberAnalysis.getTimeNetStudy()));
  }

  public RankerDTO getPoseRanker() throws NoSuchElementException {
    MemberAnalysis memberAnalysis = memberAnalysisRepository.getPoseRanker();
    return new RankerDTO(memberAnalysis.getMember(), toTimeString(memberAnalysis.getTimeGoodPose()));
  }

}
