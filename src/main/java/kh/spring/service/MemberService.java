package kh.spring.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import kh.spring.dao.MemberDAO;
import kh.spring.dto.AreaDTO;
import kh.spring.dto.AreaSavedDTO;
import kh.spring.dto.MemberDTO;
import kh.spring.dto.MyPostDTO;
import kh.spring.statics.Statics;
import kh.spring.utils.EncryptUtils;

@Service
public class MemberService {

	@Autowired
	MemberDAO memberDao;

	@Autowired
	AreaService aService;

	Gson gson = new Gson();

	// 이메일 체크
	public int emailCheck(String email) {
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
	public MemberDTO normalLoginSelectAll(String emailID, String pw) {
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.normalLoginSelectAll(emailID, encryptPw);
	}

	// SMTP 서버 정보 설정
	public Properties smtpSetting() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.naver.com");
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.naver.com");
		return props;
	}

	// PW찾기 패스워드 변경
	public int findPwChange(String findPwTargetEmail, String pw) {
		String encryptPw = EncryptUtils.getSHA512(pw);
		return memberDao.findPwChange(findPwTargetEmail, encryptPw);
	}

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

	// 마이페이지 정보 빼오기
	public MemberDTO myInfoSelectAll(int loginSeq) {
		return memberDao.myInfoSelectAll(loginSeq);
	}

	// 마이페이지 정보수정
	public int myInfoChangeOk(MemberDTO dto, MultipartFile file, String realPath)
			throws IllegalStateException, IOException {
		String fileObjectChangeString = String.valueOf(file);
		boolean isFile = fileObjectChangeString.contains("size=0");
		if (isFile) {
			return memberDao.myInfoChangeOkNoFile(dto);
		} else {
			if (!file.isEmpty()) {
				String existingPhotoStr = memberDao.existingPhotoStr(dto.getSeq());
				if (!existingPhotoStr.contains("default_profileqwerkjhgdiute.jpg")) {
					File deleteFile = new File(realPath + "\\" + existingPhotoStr);
					deleteFile.delete(); // 기존 파일삭제
				}
				File realPathFile = new File(realPath);
				if (!realPathFile.exists()) {
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
		dto.setPw("");
		String result = gson.toJson(dto);
		return result;
	}

	// 찜한 여행지 seq 불러오기
	public List<Integer> mySaveListSeq(int loginSeq, int start, int end) {
		return memberDao.mySaveListSeq(loginSeq, start, end);
	}

	// 여행지 평점 불러오기
	public String savedAreaGrade(int seq) {
		return memberDao.savedAreaGrade(seq);
	}

	// 찜목록 더보기
	public String moreSaving(int loginSeq, int btn) throws Exception {
		int start = btn;
		int end = start + Statics.SAVE_LIST_MORE;
		List<Integer> isMySaveListMore = mySaveListSeq(loginSeq, end+1, end+1);
		List<Integer> mySaveListSeq = mySaveListSeq(loginSeq, start, end);
		List<AreaSavedDTO> dto = new ArrayList<>();
		for (int saveSeq : mySaveListSeq) {
			AreaSavedDTO adto = new AreaSavedDTO(); // 상속받았는데 왜 ArrayList가 안받아지는가
			AreaDTO tdto = new AreaDTO();
			String rate = savedAreaGrade(saveSeq);
			if (rate == null) {
				adto.setSavedListRate("-");
			} else {
				adto.setSavedListRate(rate);
			}
			tdto = aService.detailBuild(saveSeq);
			adto.setSeq(saveSeq);
			adto.setName(tdto.getName());
			adto.setPhoto(tdto.getPhoto());
			adto.setIsMore(isMySaveListMore.size());
			if (tdto.getPhone().equals("null")) {
				adto.setPhone("등록된 번호가 없습니다.");
			} else {
				adto.setPhone(tdto.getPhone());
			}
			adto.setLo_detail(tdto.getLo_detail());
			adto.setDetail(tdto.getDetail());
			dto.add(adto);
		}
		return gson.toJson(dto);
	}
	
	// 내 게시글 갯수
	public int getMyPostTotalCount(int loginSeq) {
		return memberDao.getMyPostCount(loginSeq);
	}
	
	// 총 페이지 갯수
	public int getMyPostPageTotalCount(int loginSeq) {
		int totalPost = getMyPostTotalCount(loginSeq);
		int pageTotalCount = 0;
		if (totalPost % Statics.RECORD_COUNT_PER_PAGE == 0) {
			pageTotalCount = totalPost / Statics.RECORD_COUNT_PER_PAGE;
		} else {
			pageTotalCount = totalPost / Statics.RECORD_COUNT_PER_PAGE + 1;
		}
		return pageTotalCount;
	}
	
	// 내 게시글 리스트
	public List<MyPostDTO> getMyPostList(int loginSeq, int start, int end){
		return memberDao.getMyPostList(loginSeq, start, end);
	}
	
	// 네비
	public String getMyPostNavi(int loginSeq, int cpage) {
		int pageTotalCount = getMyPostPageTotalCount(loginSeq);
		int startNavi = (cpage-1) / Statics.NAVI_COUNT_PER_PAGE * Statics.NAVI_COUNT_PER_PAGE + 1;
		int endNavi = startNavi + Statics.NAVI_COUNT_PER_PAGE - 1;
		
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		boolean needPrev = true;
		boolean needNext = true;
		
		if(startNavi == 1) {
			needPrev = false;
		}

		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		
		String pageNavi = "";
		
		if(needPrev) {
			pageNavi += "<a href='/member/writenList?currentPage="+(startNavi-1)+"'><</a> ";
		}
		
		for(int i = startNavi ; i <= endNavi; i++) {
			pageNavi += "<a href='/member/writenList?currentPage="+i+"'>" + i + "</a> ";
		}
		
		if(needNext) {
			pageNavi += "<a href='/member/writenList?currentPage="+(endNavi+1)+"'>></a>";
		}
		return pageNavi;
	}
	
	// cpage조정
	public int myPostPageDefender(int loginSeq, Integer currentPage) {
		System.out.println("빌더들어오는 currentPage값 : " + currentPage);
		if(currentPage == null || currentPage == 0) {currentPage = 1;}
		System.out.println("1로 바뀐 currentPage값 : " + currentPage);
		int cpage = currentPage;
		System.out.println("current에서 cpage로 바뀐 값 : " + cpage);
		if(cpage < 1) {
			cpage = 1;
		}
		int pageTotalCount = getMyPostPageTotalCount(loginSeq);
		System.out.println("토탈카운트 얘가 0이군" + pageTotalCount);
		if(cpage > pageTotalCount) {
			cpage = pageTotalCount;
		}
		return cpage;
	}

}
