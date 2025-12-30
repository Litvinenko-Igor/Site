package com.example.demo.Telegram.service;

import com.example.demo.Car.Brand;
import com.example.demo.Car.Car;
import com.example.demo.Car.InfoModel;
import com.example.demo.Car.ModelOfCar;
import com.example.demo.Telegram.config.BotConfig;
import com.vdurmont.emoji.EmojiParser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Car car;

    private final BotConfig config;

    List<Brand> mainBran;

    static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities. \n\n" +
            "You can execute commands from the main menu on the left or by typing a command: \n\n" +
            "Types /start to see a welcome message\n\n" +
            "Type /mydata to see data stored about yourself\n\n" +
            "Type /help to see this message again";

    public TelegramBot(BotConfig config, Car car) {
        this.config = config;
        this.car = car;
        this.mainBran = car.getBrands();

        List<BotCommand> listOfCommands = new ArrayList();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/deletedata", "delete my data"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String brandName = "";
            Brand brandName2 = null;
            for (Brand brand : mainBran) {
                if(message.startsWith((brand.getClass().getSimpleName()))) {
                    message = "/car";
                    brandName = brand.getClass().getSimpleName();
                    brandName2 = brand;
                }
            }

            switch (message) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    prepareAndSendMessage(chatId, HELP_TEXT);
                    break;
                case "/car":
                    if (brandName2 == null) {
                        prepareAndSendMessage(chatId, "Спочатку обери бренд з клавіатури 👇");
                        break;
                    }
                    register(chatId, "Which type of cars " + brandName + " do you want to choice?", brandName2.getClasses1());
                    break;
                default:
                    prepareAndSendMessage(chatId, "Sorry, command was not recognized");
            }
        } else if(update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackQuery.startsWith("char:")) {
                String code = callbackQuery.substring("char:".length());
                InfoModel m = car.getByModel().get(code);
                if (m != null) {
                    characteristics(chatId, messageId, m);
                } else {
                    executeEditMessageText("Unknown model: " + code, chatId, messageId);
                }
                return;
            }

            InfoModel m = car.getByModel().get(callbackQuery);
             if(m != null) {
                executeEditMessageText("You pressed " + callbackQuery + " button", chatId, messageId);
                sendPhotoExample(chatId,callbackQuery, m.getDescription(), m);
            } else {
                executeEditMessageText("Unknown model: " + callbackQuery, chatId, messageId);
            }
        }
    }
    private void register(long chatId, String textToSend, List<Class<?>> brands) {
        SendMessage message = new SendMessage(String.valueOf(chatId), textToSend);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        int perRow = 3;
        for(Class<?> brand : brands) {
            final ModelOfCar ann = brand.getAnnotation(ModelOfCar.class); if(ann == null) continue;
            String s = ann.model().substring(ann.model().indexOf("_") + 1);
            System.out.println(s);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(s);
            button.setCallbackData(ann.model());
            rowInline.add(button);

            if(rowInline.size() == perRow) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }
        }

        if(!rowInline.isEmpty()) rowsInline.add(rowInline);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rowsInline);
        message.setReplyMarkup(markup);
        executeMessage(message);
    }


    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage(String.valueOf(chatId), textToSend);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Audi 🚗");
        row.add("BMW 🏎️");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("Mercedes 💫");
        row.add("Volkswagen 🔧");
        keyboardRows.add(row);
        keyboard.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboard);
        executeMessage(message);
    }

    private void startCommandReceived(long chatId, String name){

        String answer = EmojiParser.parseToUnicode("Hello " + name + " nice to meet you! " + ":jack_o_lantern: \n\n" +
                "What do you want to know?");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void executeEditMessageText(String text, long chatId, long messageId){
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void sendPhotoExample(long chatId, String callbackQuery, String textToSend, InfoModel brand) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        photo.setCaption(textToSend);
        System.out.println(callbackQuery);
        InputStream is = getClass().getResourceAsStream("/" + brand.getBrand() + "/" + callbackQuery + ".jpg");
        if (is != null) photo.setPhoto(new InputFile(is, callbackQuery + ".jpg"));


        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText("characteristics");
        btn.setCallbackData("char:" + brand.getModel());

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(btn);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        photo.setReplyMarkup(markup);

        try {
            execute(photo);
        } catch (TelegramApiException e) {
            log.error("Error sending photo: " + e.getMessage());
        }
    }

    private void characteristics(long chatId, long messageId, InfoModel m) {
        String text = """
            Model: %s
            Price: %.2f
            Top speed: %d
            Fuel: %s
            """.formatted(
                m.getModel(),
                m.getPrice(),
                m.getTopSpeed(),
                m.getFuelType()
        );

        EditMessageCaption edit = new EditMessageCaption();
        edit.setChatId(String.valueOf(chatId));
        edit.setMessageId((int) messageId);
        edit.setCaption(text);

        try {
            execute(edit);
        } catch (TelegramApiException e) {
            log.error("Error editing message: " + e.getMessage());
        }
    }

    private void prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
