package com.example.lottoweb.aspect

import com.example.lottoweb.exception.FrequentRequestException
import com.example.lottoweb.exception.LoginMissingException
import com.example.lottoweb.utils.annotation.CoolDown
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.cache.CacheManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * @author Unagi_zoso
 * @since 2023-11-25
 */
/**
 * 연속적으로 API를 호출하는 것을 방지하기 위한 Aspect
 * @CoolDown이 있는 API를 호출 시 300ms 동안 지속되는 값을 redis에 저장하여
 * 이 값이 존재하는 동안 이 API를 또 호출하면 예외를 발생시킵니다.
 *
 * @see com.example.lottoweb.config.RedisConfig
 * @see com.example.lottoweb.utils.annotation.CoolDown
 * @throws FrequentRequestException
 */
@Aspect
@Component
class CoolDownAspect(
    private val cacheManager: CacheManager,
) {
    @Around("@within(coolDown), @annotation(coolDown)")
    private fun coolDown(joinPoint: ProceedingJoinPoint, coolDown: CoolDown): Any {
        val key = coolDown.prefix + joinPoint.signature.name + getUsername()

        if (isCoolingDown(key)) {
            throw FrequentRequestException()
        }

        // 실행 전에 쿨다운 시작
        startCoolDown(key)

        // 메서드 실행
        return joinPoint.proceed()
    }

    private fun getUsername(): String {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        return authentication?.name ?: throw LoginMissingException()
    }

    private fun isCoolingDown(username: String) =
        cacheManager.getCache("cooldown")?.get(username) != null

    private fun startCoolDown(userId: String) {
        cacheManager.getCache("cooldown")?.put(userId, true)
    }
}
