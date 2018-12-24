package com.xaeport.crossborder.service.bondedIExit;

import com.xaeport.crossborder.data.mapper.ExitManifestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExitManifestService {

    @Autowired
    ExitManifestMapper exitManifestMapper;

}
