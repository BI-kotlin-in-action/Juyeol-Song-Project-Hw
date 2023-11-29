package com.example.lottoweb.utils.annotation

/**
 * @author Unagi_zoso
 * @since 2023-11-25
 */
// 쿨다운 시간을 설정할 때 사용하는 어노테이션입니다.
/**
 * @see com.example.lottoweb.aspect.CoolDownAspect
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CoolDown(
    val prefix: String = "cool_down_",
)
