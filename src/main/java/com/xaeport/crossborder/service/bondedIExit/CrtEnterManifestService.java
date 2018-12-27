package com.xaeport.crossborder.service.bondedIExit;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.mapper.CrtEnterManifestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CrtEnterManifestService {

    @Autowired
    CrtEnterManifestMapper crtEnterManifestMapper;

    public List<BondInvtBsc> queryCrtEnterManifestList(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryCrtEnterManifestList(paramMap);
    }

    public Integer queryCrtEnterManifestCount(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryCrtEnterManifestCount(paramMap);
    }
}
