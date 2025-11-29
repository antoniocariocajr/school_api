package com.school.services.impl;

import com.school.services.ReportCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @RequiredArgsConstructor
public class ReportCardServiceImpl implements ReportCardService {

    @Override
    public void generateForTerm(UUID schoolTermId) {

    }
}
