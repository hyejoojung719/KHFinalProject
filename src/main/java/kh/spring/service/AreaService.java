package kh.spring.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kh.spring.dto.AreaDTO;
import kh.spring.dto.AreaListDTO;
import kh.spring.statics.Statics;

@Service
public class AreaService {

	public String categorySort(String cat) {
		String result = "";
		switch(cat){
		case "A01": result = "자연"; break;
		case "A02": result = "인문"; break;
		case "A03": result = "레포츠"; break;
		case "A04": result = "쇼핑"; break;
		case "A05": result = "음식"; break;
		case "B02": result = "숙박"; break;
		case "C01": result = "추천코스"; break;
		}
		return result;
	}

	public String contentID(int contenttypeID) {
		String result = "";
		switch(contenttypeID) {
		case 12 : result = "관광지"; break;
		case 14 : result = "문화시설"; break;
		case 15 : result = "행사/공연/축제"; break;
		case 25 : result = "여행코스"; break;
		case 28 : result = "레포츠"; break;
		case 32 : result = "숙박"; break;
		case 38 : result = "쇼핑"; break;
		case 39 : result = "음식점"; break;
		}
		return result;
	}

	public int areaCode(String area) {
		int result = 0;
		switch(area) {
		case "서울": result = 1; break;
		case "인천": result = 2; break;
		case "대전": result = 3; break;
		case "대구": result = 4; break;
		case "광주": result = 5; break;
		case "부산": result = 6; break;
		case "울산": result = 7; break;
		case "세종": result = 8; break;
		case "경기": result = 31; break;
		case "강원": result = 32; break;
		case "충북": result = 33; break;
		case "충남": result = 34; break;
		case "경북": result = 35; break;
		case "경남": result = 36; break;
		case "전북": result = 37; break;
		case "전남": result = 38; break;
		case "제주": result = 39; break;
		}
		return result;

	}

	public AreaDTO detail(int target) throws Exception{
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=tASWrdaQeX%2FNZMpo1onkA8VC1ELXLdVsWav03zKKEk57adnScsDWhRK1lfKHkfQq3l7g7pRBmaB7UMa2EsWj4A%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
		urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
		urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + target); /*콘텐츠ID*/
		urlBuilder.append("&" + URLEncoder.encode("defaultYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*기본정보 조회여부*/
		urlBuilder.append("&" + URLEncoder.encode("firstImageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*원본, 썸네일 대표이미지 조회여부*/
		urlBuilder.append("&" + URLEncoder.encode("areacodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역코드, 시군구코드 조회여부*/
		urlBuilder.append("&" + URLEncoder.encode("catcodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*대,중,소분류코드 조회여부*/
		urlBuilder.append("&" + URLEncoder.encode("addrinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*주소, 상세주소 조회여부*/
		urlBuilder.append("&" + URLEncoder.encode("overviewYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*콘텐츠 개요 조회여부*/
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*Json*/

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		Gson gson = new Gson();
		gson.fromJson(sb.toString(), JsonObject.class);
		JsonObject j = (JsonObject) JsonParser.parseString(sb.toString());
		JsonObject response = (JsonObject) JsonParser.parseString(j.get("response").toString());
		JsonObject body = (JsonObject) JsonParser.parseString(response.get("body").toString());
		JsonObject items = (JsonObject) JsonParser.parseString(body.get("items").toString());
		JsonObject item = (JsonObject) JsonParser.parseString(items.get("item").toString());
		String name = item.get("title").toString(); //타이틀
		name = name.substring(1,name.length()-1);
		String category = item.get("cat1").toString(); //카테고리
		category = category.substring(1,category.length()-1);
		category = categorySort(category);
		String location = item.get("areacode").toString(); //지역코드		
		String lo_detail = item.get("addr1").toString(); //주소
		lo_detail = lo_detail.substring(1,lo_detail.length()-1);
		String tel = "null";
		if(item.has("tel")) {
			tel = item.get("tel").toString().replace("\"", "");		
		}
		String detail = item.get("overview").toString().replace("\"", ""); //상세설명
		detail = detail.replace("\\", "<br>");
		detail = detail.replace("n", "");
		detail = detail.replace("\"", "");
		
		String homepage = "null";
		if(item.has("homepage")) {
			homepage = item.get("homepage").toString(); //홈페이지	
			homepage = homepage.replace("\"", "");
			homepage = homepage.replace("\\", "");
		}
		String photo = "null"; //이미지
		if(item.has("firstimage")) {
			photo = item.get("firstimage").toString().replace("\"", "");
		}





		AreaDTO dto = new AreaDTO(name, category, location,lo_detail,tel,detail,homepage,photo);
		return dto;
	}

	//일반
	public List<AreaListDTO> list(int pageNum, int conID, int areaCode) throws Exception{
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=tASWrdaQeX%2FNZMpo1onkA8VC1ELXLdVsWav03zKKEk57adnScsDWhRK1lfKHkfQq3l7g7pRBmaB7UMa2EsWj4A%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + Statics.areaViewNo); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNum); /*현재페이지 번호*/
		urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
		urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
		urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("P", "UTF-8")); /*조회순 정렬*/
		if(conID!=0) {
			urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + conID); /*관광타입(관광지, 숙박 등) ID*/			
		}
		if(areaCode!=0) {
			urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + areaCode); /*지역코드*/			
		}
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*Json*/


		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();


		List<AreaListDTO> list = new ArrayList<>();
		JsonObject j = (JsonObject) JsonParser.parseString(sb.toString());
		JsonObject response = (JsonObject) JsonParser.parseString(j.get("response").toString());
		JsonObject body = (JsonObject) JsonParser.parseString(response.get("body").toString());
		JsonObject items = (JsonObject) JsonParser.parseString(body.get("items").toString());
		JsonArray item = (JsonArray)JsonParser.parseString(items.get("item").toString());

		for(int i = 0; i< item.size();i++) {
			JsonObject tmp = (JsonObject) item.get(i);
			String contentid = tmp.get("contentid").toString();
			String title = tmp.get("title").toString();
			title = title.substring(1,title.length()-1);
			int contenttype = tmp.get("contenttypeid").getAsInt();
			String contenttypeid = contentID(contenttype);
			String addr1 = "null";
			if(tmp.has("addr1")) {
				addr1=	tmp.get("addr1").toString();
			}
			addr1 = addr1.substring(1,addr1.length()-1);
			String cat1 = tmp.get("cat1").toString();
			cat1 = categorySort(cat1.substring(1,cat1.length()-1));
			int areacode = tmp.get("areacode").getAsInt();
			String cat3 = tmp.get("cat3").toString();
			cat3 = cat3.substring(1,cat3.length()-1);
			String firstimage = "";
			if(tmp.has("firstimage")){
				firstimage = tmp.get("firstimage").toString();
				firstimage = firstimage.substring(1,firstimage.length()-1);
			}else{
				firstimage ="null";
			}
			int readcount = tmp.get("readcount").getAsInt();
			AreaListDTO dto = new AreaListDTO(title,contentid,contenttypeid,addr1,areacode,cat1,cat3,firstimage,readcount);
			list.add(dto);
		}
		return list;
	}

	//검색
	public List<AreaListDTO> search(int pageNum, int conID, int areaCode,String target) throws Exception{
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=tASWrdaQeX%2FNZMpo1onkA8VC1ELXLdVsWav03zKKEk57adnScsDWhRK1lfKHkfQq3l7g7pRBmaB7UMa2EsWj4A%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + Statics.areaViewNo); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNum); /*현재페이지 번호*/
		urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
		urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
		urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("P", "UTF-8")); /*조회순 정렬*/
		if(conID!=0) {
			urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + conID); /*관광타입(관광지, 숙박 등) ID*/			
		}
		if(areaCode!=0) {
			urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + areaCode); /*지역코드*/			
		}
		urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(target, "UTF-8")); /*검색어*/
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*Json*/


		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();


		List<AreaListDTO> list = new ArrayList<>();
		JsonObject j = (JsonObject) JsonParser.parseString(sb.toString());
		JsonObject response = (JsonObject) JsonParser.parseString(j.get("response").toString());
		JsonObject body = (JsonObject) JsonParser.parseString(response.get("body").toString());
		if(body.get("items").isJsonObject()) {
			JsonObject items = (JsonObject) JsonParser.parseString(body.get("items").toString());
			System.out.println(items.toString());
			if(JsonParser.parseString(items.get("item").toString()).isJsonArray()){
				JsonArray item = (JsonArray)JsonParser.parseString(items.get("item").toString());

				for(int i = 0; i< item.size();i++) {
					JsonObject tmp = (JsonObject) item.get(i);
					String contentid = tmp.get("contentid").toString();
					String title = tmp.get("title").toString();
					title = title.substring(1,title.length()-1);
					int contenttype = tmp.get("contenttypeid").getAsInt();
					String contenttypeid = contentID(contenttype);
					String addr1 = "null";
					if(tmp.has("addr1")) {
						addr1=	tmp.get("addr1").toString();
					}
					addr1 = addr1.substring(1,addr1.length()-1);
					String cat1 = tmp.get("cat1").toString();
					cat1 = categorySort(cat1.substring(1,cat1.length()-1));
					int areacode = tmp.get("areacode").getAsInt();
					String cat3 = tmp.get("cat3").toString();
					cat3 = cat3.substring(1,cat3.length()-1);
					String firstimage = "";
					if(tmp.has("firstimage")){
						firstimage = tmp.get("firstimage").toString();
						firstimage = firstimage.substring(1,firstimage.length()-1);
					}else{
						firstimage ="null";
					}
					int readcount = tmp.get("readcount").getAsInt();
					AreaListDTO dto = new AreaListDTO(title,contentid,contenttypeid,addr1,areacode,cat1,cat3,firstimage,readcount);
					list.add(dto);
				}
			}else {
				JsonObject item = (JsonObject)JsonParser.parseString(items.get("item").toString());
				String contentid = item.get("contentid").toString();
				String title = item.get("title").toString();
				title = title.substring(1,title.length()-1);
				int contenttype = item.get("contenttypeid").getAsInt();
				String contenttypeid = contentID(contenttype);
				String addr1 = "null";
				if(item.has("addr1")) {
					addr1=	item.get("addr1").toString();
				}
				addr1 = addr1.substring(1,addr1.length()-1);
				String cat1 = item.get("cat1").toString();
				cat1 = categorySort(cat1.substring(1,cat1.length()-1));
				int areacode = item.get("areacode").getAsInt();
				String cat3 = item.get("cat3").toString();
				cat3 = cat3.substring(1,cat3.length()-1);
				String firstimage = "";
				if(item.has("firstimage")){
					firstimage = item.get("firstimage").toString();
					firstimage = firstimage.substring(1,firstimage.length()-1);
				}else{
					firstimage ="null";
				}
				int readcount = item.get("readcount").getAsInt();
				AreaListDTO dto = new AreaListDTO(title,contentid,contenttypeid,addr1,areacode,cat1,cat3,firstimage,readcount);
				list.add(dto);
			}
		}

		return list;
	}


	public int[] pageCount(int pageNum, int conID, int areaCode) throws Exception{
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=tASWrdaQeX%2FNZMpo1onkA8VC1ELXLdVsWav03zKKEk57adnScsDWhRK1lfKHkfQq3l7g7pRBmaB7UMa2EsWj4A%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + Statics.areaViewNo); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNum); /*현재페이지 번호*/
		urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
		urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
		urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("P", "UTF-8")); /*조회순 정렬*/
		if(conID!=0) {
			urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + conID); /*관광타입(관광지, 숙박 등) ID*/			
		}
		if(areaCode!=0) {
			urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + areaCode); /*지역코드*/			
		}
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*Json*/


		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();


		JsonObject j = (JsonObject) JsonParser.parseString(sb.toString());
		JsonObject response = (JsonObject) JsonParser.parseString(j.get("response").toString());
		JsonObject body = (JsonObject) JsonParser.parseString(response.get("body").toString());
		int result[] = new int[2]; 
		result[0] = body.get("totalCount").getAsInt()/Statics.areaViewNo;
		if(body.get("totalCount").getAsInt()%Statics.areaViewNo!= 0 ) {result[0]++;}
		result[1] = body.get("pageNo").getAsInt();
		return result;
	}

	public int[] searchCount(int pageNum, int conID, int areaCode,String target) throws Exception{
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=tASWrdaQeX%2FNZMpo1onkA8VC1ELXLdVsWav03zKKEk57adnScsDWhRK1lfKHkfQq3l7g7pRBmaB7UMa2EsWj4A%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + Statics.areaViewNo); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNum); /*현재페이지 번호*/
		urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
		urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
		urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("P", "UTF-8")); /*조회순 정렬*/
		if(conID!=0) {
			urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + conID); /*관광타입(관광지, 숙박 등) ID*/			
		}
		if(areaCode!=0) {
			urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + areaCode); /*지역코드*/			
		}
		urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(target, "UTF-8")); /*검색어*/
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*Json*/


		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();


		JsonObject j = (JsonObject) JsonParser.parseString(sb.toString());
		JsonObject response = (JsonObject) JsonParser.parseString(j.get("response").toString());
		JsonObject body = (JsonObject) JsonParser.parseString(response.get("body").toString());
		int result[] = new int[2]; 
		result[0] = body.get("totalCount").getAsInt()/Statics.areaViewNo;
		if(body.get("totalCount").getAsInt()%Statics.areaViewNo!= 0 ) {result[0]++;}
		result[1] = body.get("pageNo").getAsInt();
		return result;
	}

	public List<Integer> paging(int total, int page) {
		if(page%10==0) {
			page--;
		}
		int target = page/10*10+1;

		List<Integer> result = new ArrayList<>();
		if(target != 1) {
			result.add(-1);
		}

		if(total>target+10) {
			for(int i = 0;i<10;i++) {
				result.add(target++);
				if(i==9 && target<total) {
					result.add(-2);
				}
			}
		}else {
			for(;target<total;target++) {
				result.add(target);
			}
		}

		if(result.size()==0) {
			result.add(1);
		}

		return result;
	}
}
