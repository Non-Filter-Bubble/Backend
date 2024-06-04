//package com.example.book_service.service.AI;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Map;
//
//public class AIDataService {
//    private final AIDataRepository aiDataRepository;
//
//    @Autowired
//    public AIDataService(AIDataRepository aiDataRepository) {
//        this.aiDataRepository = aiDataRepository;
//    }
//
//    public void saveAIData(Map<String, Object> responseData) {
//        // AIData 테이블에 저장할 데이터 생성
//        AIData aiData = new AIData();
//        aiData.setResult(responseData.toString()); // 결과를 문자열로 변환하여 저장하거나, 필요한 데이터만 저장합니다.
//
//        // AIData 테이블에 데이터 저장
//        aiDataRepository.save(aiData);
//    }
//}
