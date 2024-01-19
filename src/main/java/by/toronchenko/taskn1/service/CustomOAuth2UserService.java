package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.entity.GitHubUser;
import by.toronchenko.taskn1.repositories.GitHubUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final GitHubUserRepository gitHubUserRepository;

    @Autowired
    public CustomOAuth2UserService(GitHubUserRepository gitHubUserRepository) {
        this.gitHubUserRepository = gitHubUserRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        return this.gitHubUserRepository.findByName((String) attributes.get("login")).orElseGet(() ->
                gitHubUserRepository.save(GitHubUser.builder()
                .id((Integer) attributes.get("id"))
                .name((String) attributes.get("login"))
                .build()));
    }

}
