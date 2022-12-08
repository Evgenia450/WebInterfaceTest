import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardApplicationFormTest {
    @BeforeEach
    void openURL() {
        open("http://localhost:9999/");
    }
    @Test
    void shouldSubmit() {
        SelenideElement form =$("form");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldBe(visible).shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitEmptyForm() {
        SelenideElement form = $("form");
        form.$("[type='button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitEmptyName() {
        SelenideElement form = $("form");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitEngName() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("John Doe");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitSymbolName() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("@!");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitSpaceName() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue(" ");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitEmptyPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldNotSubmitIncorrectPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=phone] input").setValue("8-993-44-11-00");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldSendARequestMistakeWithoutACheckbox() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79781234567");
        form.$("[type=button]").click();
        $(".input_invalid [class=checkbox__text]").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}