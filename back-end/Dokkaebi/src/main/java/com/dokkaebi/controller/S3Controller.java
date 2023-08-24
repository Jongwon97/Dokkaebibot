package com.dokkaebi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dokkaebi.service.studyroom.StudyRoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/s3")
@RestController
public class S3Controller {
		
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    private final AmazonS3 s3Client;
    private StudyRoomService service;
    @Autowired
    public S3Controller(AmazonS3 s3Client, StudyRoomService service) {
        this.s3Client = s3Client;
        this.service=service;
    }
    
    @PostMapping("/upload")
    public String S3FileUpload(@RequestParam("file") MultipartFile file, 
    		@RequestParam("roomId") Long roomId) {
        try {
        	String folderName = "images"+"/"+roomId; // 원하는 폴더 이름
            String fileName = folderName + "/" +file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            // 파일을 AWS S3에 업로드
            s3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
            
            // 생성한 스터디룸 이미지 주소 수정
            service.setStudyRoomImage("https://project-dokkaebi.s3.ap-northeast-2.amazonaws.com/"+fileName, roomId);
            
            // 업로드가 완료된 후의 로직을 작성할 수 있습니다.
            return "success"; // 업로드 성공 시, 클라이언트에게 응답
        } catch (Exception e) {
            log.error("S3 업로드 실패: " + e.getMessage());
            return "error"; // 업로드 실패 시, 클라이언트에게 응답
        }	
    }
}
