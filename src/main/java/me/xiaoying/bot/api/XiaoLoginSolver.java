package me.xiaoying.bot.api;

import javafx.application.Application;
import kotlin.coroutines.Continuation;
import me.xiaoying.bot.cache.Caches;
import me.xiaoying.bot.constant.ConstantCommon;
import me.xiaoying.bot.gui.MainUI;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.DeviceVerificationRequests;
import net.mamoe.mirai.utils.DeviceVerificationResult;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class XiaoLoginSolver extends LoginSolver {
    @Nullable
    @Override
    public Object onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        return null;
    }

    /**
     * 防止服务器发送 {当前QQ版本过低，请升级至最新版本后再登录}
     *
     * @return
     */
    @Override
    public boolean isSliderCaptchaSupported() {
        return true;
    }

    @Nullable
    @Override
    public Object onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String url, @NotNull Continuation<? super String> continuation) {
        Thread thread = new Thread(() -> Application.launch(MainUI.class));
        thread.setName("thread-login-solver");
        thread.start();
        Caches.threadCaches.put("thread-login-solver", thread);
        ConstantCommon.QQ_AUTHORIZE_HREF = url;
//        Scanner scan = new Scanner(System.in);


        while(thread.isAlive()) {

        }
        return ConstantCommon.QQ_AUTHORIZE_TICKET;
    }

    /**
     * 处理不安全设备验证.
     * <p>
     * 返回值保留给将来使用. 目前在处理完成后返回任意内容 (包含 `null`) 均视为处理成功.
     * <p>
     * ## 异常类型
     * <p>
     * 抛出一个 [LoginFailedException] 以正常地终止登录, 并可建议系统进行重连或停止 bot (通过 [LoginFailedException.killBot]).
     * 例如抛出 [RetryLaterException] 可让 bot 重新进行一次登录.
     * <p>
     * 抛出任意其他 [Throwable] 将视为验证码解决器的自身错误.
     *
     * @return 任意内容. 返回值保留以供未来更新.
     */
    @Nullable
    @Override
    public String onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String url, @NotNull Continuation<? super String> continuation) {
        return Objects.requireNonNull(onSolveSliderCaptcha(bot, url, continuation)).toString();
    }

    @Nullable
    @Override
    public Object onSolveDeviceVerification(@NotNull Bot bot, @NotNull DeviceVerificationRequests requests, @NotNull Continuation<? super DeviceVerificationResult> $completion) {
        DeviceVerificationRequests.FallbackRequest fallback = requests.getFallback();
        String url = fallback.getUrl();//HTTP URL. 可能需要在 QQ 浏览器中打开并人工操作.
        System.out.println(url);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        DeviceVerificationResult solved1 = fallback.solved();//通知此请求已被解决. 获取 [DeviceVerificationResult] 用于返回 [LoginSolver.onSolveDeviceVerification].
        return super.onSolveDeviceVerification(bot, requests, $completion);
    }

}