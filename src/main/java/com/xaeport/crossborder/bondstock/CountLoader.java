package com.xaeport.crossborder.bondstock;

import com.xaeport.crossborder.data.entity.*;

import java.util.List;
import java.util.Map;

//账册表体进行预增，实增，预减，实减接口
public interface CountLoader {

    void count(BondInvtBsc bondInvtBsc);

    int count(Map<String, Object> excelMap, Users user, String emsNo);

    void count(PassPortHead passPortHead);

    int count(List<ImpInventoryBody> impInventoryBodyList, Enterprise enterpriseDetail);
}
