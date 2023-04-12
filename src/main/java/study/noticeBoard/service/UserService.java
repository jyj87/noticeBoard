package study.noticeBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;


    /* 회원가입 */
    @Transactional
    public Long userJoin(UserDto.Request dto){
        log.info("UserService userJoin() 실행");
        dto.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(dto.toEntity()).getId();

    }
    /* 회원아이디 조회 */
    @Transactional
    public boolean userSerach(UserDto.Request dto){

        return false;
    }

    /* 회원가입시, 유효성 검사 및 중복 체크 */
    @Transactional(readOnly = true)
    public Map<String,String> validateHandling(Errors errors){
        
        Map<String,String> validatorResult = new HashMap<>();

        /* 유효성, 중복 검사에서 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()){
            String keyName = String.format("valid_%s",error.getField());
            validatorResult.put(keyName,error.getDefaultMessage());
        }
        return validatorResult;
    }


}
