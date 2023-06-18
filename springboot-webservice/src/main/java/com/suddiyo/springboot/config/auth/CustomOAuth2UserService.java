package com.suddiyo.springboot.config.auth;

import com.suddiyo.springboot.config.auth.dto.OAuthAttributes;
import com.suddiyo.springboot.config.auth.dto.SessionUser;
import com.suddiyo.springboot.domain.user.Member;
import com.suddiyo.springboot.domain.user.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        /*
        registrationId: 현재 로그인 진행중인 서비스를 구분하는 코드
        ex) Google login or Naver login
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        /*
        userNameAttributeName: OAuth2 로그인 진행 시 키가 되는 필드값. 즉, PK와 같은 의미
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        /*
         OAuthAttributes: OAuth2User의 attribute를 담을 클래스. 서비스와 무관하게 관리하기 위해 정의.
         */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        /*
        SessionUser: 인증된 사용자의 정보를 담는 클래스
        User는 엔티티이기 때문에 직렬화를 하게 될 경우, 자식들까지 직렬화 되어 쿼리가 나갈 가능성 있음.
        따라서, 직렬화 기능을 가진 dto를 하나 더 추가하는 게 유지보수성에 좋음
         */
        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(member));

        return new DefaultOAuth2User(
                Collections.singleton( new SimpleGrantedAuthority(member.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey()
        );
    }

    /*
    로그인 한 사용자의 정보(이름, 프로필 사진)이 변경 되었을 때 자동으로 update
     */
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(member);
    }
}
