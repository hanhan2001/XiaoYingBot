package me.xiaoying.bot.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.xiaoying.bot.constant.ConstantCommon;

import java.util.Objects;

/**
 * 界面
 */
public class MainUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView web = new WebView();
        WebEngine webEngine = web.getEngine();
        web.setPrefWidth(565);
        web.setPrefHeight(288);

        webEngine.setJavaScriptEnabled(true);
//        webEngine.setUserAgent("Mozilla/5.0 (Linux; Android 5.1; OPPO R9tm Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043128 Safari/537.36 V1_AND_SQ_7.0.0_676_YYB_D PA QQ/7.0.0.3135 NetType/4G WebP/0.3.0 Pixel/1080");
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
        webEngine.load(Objects.requireNonNull(MainUI.class.getResource("/Bot-Frame/index/login-authorize/index.html")).toExternalForm());

        AnchorPane root = new AnchorPane();
        root.getChildren().add(web);
        Scene scene = new Scene(root);

        primaryStage.getIcons().add(new Image(Objects.requireNonNull(MainUI.class.getResource("/images/QQ.png")).toString()));
        primaryStage.setScene(scene);
        primaryStage.setWidth(565);
        primaryStage.setHeight(288);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (ConstantCommon.QQ_AUTHORIZE_TICKET.isEmpty())
                    primaryStage.show();
            }
        });
    }
}