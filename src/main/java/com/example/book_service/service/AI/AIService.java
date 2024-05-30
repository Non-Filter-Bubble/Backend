//package com.example.book_service.service.AI;
//
//import com.example.book_service.dto.AIData.AIModelData;
//import com.example.book_service.entity.AIData;
//import com.example.book_service.entity.UserEntity;
//import com.example.book_service.repository.AIDataRepository;
//import com.example.book_service.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AIService {
//    private final AIDataRepository aiDataRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public AIService(AIDataRepository aiDataRepository, UserRepository userRepository) {
//        this.aiDataRepository = aiDataRepository;
//        this.userRepository = userRepository;
//    }
//
//    // AI가 보낸 데이터를 DB에 저장하는 메서드
//    public void saveDataToDB(AIModelData aiModelData, String username) {
//        // AI가 보낸 데이터와 사용자 정보를 함께 DB에 저장하는 로직을 작성합니다.
//        // 여기서는 간단한 예시로 데이터를 출력하는 것으로 대체합니다.
//        System.out.println("Saving data to DB: " + aiModelData.toString());
//        System.out.println("User Id: " + username);
//
//        // 사용자를 찾아서 AIData에 연결합니다.
//
//        UserEntity user = userRepository.findByUsername(username); // userRepository는 사용자를 조회하는 데 사용됩니다.
//
//
//
//        AIData aiData = new AIData();
//        aiData.setDataList(aiModelData.getIntegerDataList()); // AI 모델 데이터 설정
//        aiData.setUser(user); // 사용자 정보 설정
//
//        // DB에 데이터를 저장하는 예시 코드
//        aiDataRepository.save(aiData);
//    }
//}
