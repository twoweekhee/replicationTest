//package com.test.replicationtest.jwt;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//
//@Service
//@RequiredArgsConstructor
//public class JwtService {
//
//    private final JwtProvider jwtProvider;
//
//    public void saveRefreshToken(Authentication authentication) {
//        entity.updateRefreshToken(newRefreshToken);
//        if(entity instanceof User)
//            userRepository.save((User) entity);
//        else if(entity instanceof Store)
//            storeRepository.save((Store) entity);
//        else
//            throw new IllegalArgumentException("Invalid Role");
//    }
//
//    public void addRefreshTokenToCookie(final HttpServletRequest request, final HttpServletResponse response,
//                                        final String refreshToken) {
//        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
//        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
//        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
//    }
//
//    public String generateToken(EmailAuthenticateAble entity, Duration duration) {
//        return jwtProvider.generateToken(entity, duration);
//    }
//
//    public void refresh(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
//        Cookie[] cookies = httpRequest.getCookies();
//        if(cookies == null || cookies.length == 0)
//            throw new GrayPantsException(ExceptionStatus.INVALID_REFRESH_TOKEN);
//        for(Cookie cookie : cookies) {
//            if(cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
//                String refreshToken = cookie.getValue();
//                Optional<User> user = userRepository.findByRefreshToken(refreshToken);
//                if(user.isPresent()) {
//                    String accessToken = generateToken(user.get(), ACCESS_TOKEN_DURATION);
//                    httpResponse.setHeader("access-token", accessToken);
//                    return ;
//                }
//                break;
//            }
//        }
//        throw new GrayPantsException(ExceptionStatus.INVALID_REFRESH_TOKEN);
//    }
//}
