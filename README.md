# 스프링프레임워크로 카카오봇 만들기

참조문서: https://github.com/plusfriend/auto_reply

## 1. 개발환경
* Ubuntu 16.04 LTS
* Eclipse Mars.2 (STS플러그인)
* SpringFramework 4.3.3 (부트 아닙니다~~)
* Jackson2 2.7.2

## 2. Jackson2 pom.xml 추가
	<!-- Jackson2 -->
	<dependency>
		<groupId>com.fasterxml.jackson.core</groupId>
		<artifactId>jackson-databind</artifactId>
		<version>2.7.2</version>
	</dependency>

## 3. Object
카카오톡 플러스친구/옐로아이디 API v. 2.0 에서는 Object로 Keyboard, Message, Photo, MessageButton을 구현해야 한다.

각각 *VO.java로 만들고, getter/setter를 만든다. KeyboardVO같은 경우, 버튼타입으로만 쓰이므로 생성자를 통해 간결하게 만들어준다.

## 4. 컨트롤러
스프링 4.0부터는 @RestController 어노테이션을 지원한다. 쉽게 생각하면 @Controller + @ResponseBody 이다. 따라서 @ResponseBody는 모든 메소드에서 생략해도 된다.

### 4-1. 키보드 구현

	@RequestMapping(value = "/keyboard", method = RequestMethod.GET)
	public KeyboardVO keyboard()
	{
		KeyboardVO keyboard=new KeyboardVO(new String[] {"사진", "라벨", "에코메세지"});
	
		return keyboard;
	}

스크린샷

![키보드사진](http://img1.daumcdn.net/thumb/R1920x0/?fname=http%3A%2F%2Fcfile4.uf.tistory.com%2Fimage%2F250DC239591385EF0C3719)

### 4-2. 메세지 구현
상대방이 메세지를 보내면 그걸 받아와서 @RequestBody로 POJO에 매핑한다. 여기서는 RequestMessageVO.java로 작성했다. 

	public class RequestMessageVO
	{
		private String user_key;
		private String type;
		private String content;
		
		//getter,setter()
	}

컨트롤러.java에서는 vo.getContent()에 따라 여러 분기처리를 한다. 여기서는 '사진' 에 샘플사진을 출력하고, '라벨'에 링크를 첨부하였으며 이외 메세지는 전부 에코(되돌려줌)처리하였다.

본 소스에는 없지만, '사진'과 '라벨'을 함께 쓰는 응답메세지도 가능하다. 또한 특정 메세지는 키보드버튼을 연속적으로 호출할 수도 있다.(response에서 키보드 객체가 null이거나 type이 text일 때만 자유롭게 대화를 주고받을 수 있다.)


	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public ResponseMessageVO message(@RequestBody RequestMessageVO vo)
	{
		ResponseMessageVO res_vo=new ResponseMessageVO();
		MessageVO mes_vo=new MessageVO();
		
		if(vo.getContent().equals("메인화면"))
		{
			mes_vo.setText("첫 화면을 호출합니다.");
			
			KeyboardVO keyboard=new KeyboardVO(new String[] {"사진", "라벨", "에코메세지"});
			
			res_vo.setKeyboard(keyboard);
		}
		else if(vo.getContent().equals("사진"))
		{
			PhotoVO photo=new PhotoVO();
			
			photo.setUrl("http://placehold.it/640x480.jpg");
			photo.setWidth(640);
			photo.setHeight(480);
			
			mes_vo.setPhoto(photo);
			mes_vo.setText(vo.getContent());
		}
		else if(vo.getContent().equals("라벨"))
		{
			MessageButtonVO messageButton=new MessageButtonVO();
			
			messageButton.setLabel("GITHUB");
			messageButton.setUrl("https://github.com/sjh836/Spring_KakaoBot_Sample");
			
			mes_vo.setMessage_button(messageButton);
			mes_vo.setText("많은 피드백부탁드립니다! STAR는 개발자를 춤추게 만들어요!");
		}
		else //에코메시지
		{
			mes_vo.setText(vo.getContent());
		}
		
		res_vo.setMessage(mes_vo);
		return res_vo;
	}

스크린샷

![메세지사진](http://img1.daumcdn.net/thumb/R1920x0/?fname=http%3A%2F%2Fcfile26.uf.tistory.com%2Fimage%2F22107639591385F00817E7)

## 5. 카카오 이모티콘 사용하기
카카오 챗봇에 이모티콘을 넣고 싶은 분들이 있을거다. 사용할 수 있으며, 단순히 (텍스트)가 끝이다. 다만 카카오톡 클라이언트에서 이걸 이모티콘으로 해석한다.

사용가능한 이모티콘은 다음과 같다. 사진과 일일히 매칭해서 제공하진 못했으나ㅠㅠ 순차적으로..

(하트뿅)(하하)(우와)(심각)(힘듦)(흑흑)(아잉)(찡긋)(뿌듯)(깜짝)(빠직)(짜증)(제발)(씨익)(신나)(헉)(열받아)(흥)(감동)(뽀뽀)(멘붕)(정색)(쑥스)(꺄아)(좋아)(굿)(훌쩍)(허걱)(부르르)(푸하하)(발그레)(수줍)(컴온)(졸려)(미소)(윙크)(방긋)(반함)(눈물)(절규)(크크)(메롱)(잘자)(잘난척)(헤롱)(놀람)(아픔)(당황)(풍선껌)(버럭)(부끄)(궁금)(흡족)(깜찍)(으으)(민망)(곤란)(잠)(행복)(안도)(우웩)(외계인)(외계인녀)(공포)(근심)(악마)(썩소)(쳇)(야호)(좌절)(삐침)(하트)(실연)(별)(브이)(오케이)(최고)(최악)(그만)(땀)(알약)(밥)(커피)(맥주)(소주)(와인)(치킨)(축하)(음표)(선물)(케이크)(촛불)(컵케이크a)(컵케이크b)(해)(구름)(비)(눈)(똥)(근조)(딸기)(호박)(입술)(야옹)(돈)(담배)(축구)(야구)(농구)(당구)(골프)(카톡)(꽃)(총)(크리스마스)(콜)

**PS.** 보통 카톡봇으로 급식메뉴나 버스스케줄을 크롤러로 긁어오는 서비스를 많이들 하는 것같다. java진영에서도 jsoup같은 걸로 파싱파싱재미를 볼 수 있다. 아니면 문제은행을 카톡봇으로 서비스할 수도 있겠다.