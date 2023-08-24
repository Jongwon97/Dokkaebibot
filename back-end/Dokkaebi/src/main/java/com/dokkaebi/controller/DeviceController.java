package com.dokkaebi.controller;

import com.dokkaebi.service.DeviceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/api/device")
@RestController
@RequiredArgsConstructor
public class DeviceController {

  private final DeviceService deviceService;

  @PostMapping("{serialNumber}/check")
  public ResponseEntity<?> registerDevice(
      HttpServletRequest httpServletRequest,
      @PathVariable String serialNumber) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    if (deviceService.registerDevice(memberId, serialNumber)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

}
