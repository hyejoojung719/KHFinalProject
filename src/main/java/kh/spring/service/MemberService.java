package kh.spring.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import kh.spring.dao.MemberDAO;
import kh.spring.dto.AreaDTO;
import kh.spring.dto.AreaSavedDTO;
import kh.spring.dto.MemberDTO;
import kh.spring.utils.EncryptUtils;

@Service
public class MemberService {

	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	AreaService aService;
	
	// 이메일 체크
	public int emailCheck(String email){
		return memberDao.emailCheck(email);
	}
	
	// 닉네임 체크
	public int nickNameCheck(String nickName) {
		return memberDao.nickNameCheck(nickName);
	}
	
	// 휴대폰 체크
	public int phoneCheck(String phone) {
		return memberDao.phoneCheck(phone);
	}
	
	// 일반 회원가입
	public int normalSignup(String emailID, String nick, String phone, String pw, String gender) {
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.normalSignup(emailID, nick, phone, encryptPw, gender);
	}
	
	// 일반 로그인체크
	public int normalLoginCheck(String emailID, String pw) {
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.normalLoginCheck(emailID, encryptPw);
	}
	
	// 일반 사용자정보 빼오기
	public MemberDTO normalLoginSelectAll(String emailID, String pw){
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.normalLoginSelectAll(emailID, encryptPw);
	}
	
	// PW찾기 패스워드 변경
	public int findPwChange(String findPwTargetEmail, String pw) {
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.findPwChange(findPwTargetEmail, encryptPw);
	}
	
	///// 카카오 로그인 /////
	// 카카오 사용자 가입여부 확인
	public int kakaoLoginLookup(int kakaoLoginId) {
		return memberDao.kakaoLoginLookup(kakaoLoginId);
	}
	
	// 카카오 사용자 정보 빼오기
	public MemberDTO kakaoLoginSelectAll(int kakaoLoginId) { // 오버로딩해도 될거같은데 헷갈릴듯
		return memberDao.kakaoLoginSelectAll(kakaoLoginId);
	}
	
	// 카카오 로그인사용자 회원가입 시키기
	public int kakaoSignup(String kakaoLoginEmail, String kakaoLoginNick, int kakaoLoginId) {
		return memberDao.kakaoSignup(kakaoLoginEmail, kakaoLoginNick, kakaoLoginId);
	}
	///// 카카오 로그인 끝 /////
	
	// 마이페이지 정보 빼오기
	public MemberDTO myInfoSelectAll(int loginSeq) {
		return memberDao.myInfoSelectAll(loginSeq);
	}
	
	// 마이페이지 정보수정
	public int myInfoChangeOk(MemberDTO dto, MultipartFile file, String realPath) throws IllegalStateException, IOException {
		String fileObjectChangeString = String.valueOf(file);
		boolean isFile = fileObjectChangeString.contains("size=0");
		if(isFile) {
			return memberDao.myInfoChangeOkNoFile(dto);
		} else {
			if(!file.isEmpty()) {
				String existingPhotoStr = memberDao.existingPhotoStr(dto.getSeq());
				if(!existingPhotoStr.contains("default_profile.png")) {
					File deleteFile = new File(realPath + "\\" + existingPhotoStr);
					deleteFile.delete(); // 기존 파일삭제
				}
				File realPathFile = new File(realPath);
				if(!realPathFile.exists()) {
					realPathFile.mkdir();
				}
				String oriName = file.getOriginalFilename();
				String sysName = UUID.randomUUID() + "_" + oriName;
				file.transferTo(new File(realPath + "/" + sysName));
				dto.setPhoto(sysName);
			}
			return memberDao.myInfoChangeOk(dto);
		}
	}
	
	// 마이페이지 기존프로필사진 정보 불러오기
	public String existingPhotoStr(int seq) {
		return memberDao.existingPhotoStr(seq);
	}
	
	// 마이페이지 비밀번호 수정
	public int myInfoPwChange(String pw) {
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.myInfoPwChange(encryptPw);
	}
	
	// 회원탈퇴
	public int deleteAccount(int seq) {
		return memberDao.deleteAccount(seq);
	}
	
	// 상대 회원 조회
	public String showMember(int mem_seq) {
		MemberDTO dto = memberDao.myInfoSelectAll(mem_seq);
		Gson gson = new Gson();
		dto.setPw("");
		String result = gson.toJson(dto);
		return result;
	}
	
	///////// 찜목록 시작 ///////////
	public List<Integer> mySaveListSeq(int loginSeq, int start, int end){
		return memberDao.mySaveListSeq(loginSeq, start, end);
	}
	
	public String savedAreaGrade(int seq) {
		return memberDao.savedAreaGrade(seq);
	}
	
	public String moreSaving(int loginSeq, int btn) throws Exception{
		Gson gson = new Gson();
		int start = btn;
		int end = start + 7;
		List<Integer>mySaveListSeq = mySaveListSeq(loginSeq, start, end);
		List<AreaSavedDTO> dto = new ArrayList<>();
		for(int saveSeq : mySaveListSeq) {
			AreaSavedDTO adto = new AreaSavedDTO(); // 상속받았는데 왜 ArrayList가 안받아지는가
			AreaDTO tdto = new AreaDTO();
			String rate = savedAreaGrade(saveSeq);
			if(rate == null) {
				adto.setSavedListRate("등록된 평점이 없습니다.");
			} else {
				adto.setSavedListRate(rate.substring(0, 3));
			}
			tdto = aService.detailBuild(saveSeq);
			adto.setSeq(saveSeq);
			adto.setName(tdto.getName());
			adto.setPhoto(tdto.getPhoto());
			adto.setPhone(tdto.getPhone());
			adto.setLo_detail(tdto.getLo_detail());
			adto.setDetail(tdto.getDetail());
			dto.add(adto);
		}
		return gson.toJson(dto);
	}
	
	
}
