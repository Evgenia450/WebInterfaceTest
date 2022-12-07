import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardApplicationFormTest {
    @Test
    void shouldSubmit() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldBe(visible).shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitEmptyForm() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitEmptyName() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitEngName() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("John Doe");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitSymbolName() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("@!");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitSpaceName() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue(" ");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitEmptyPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldNotSubmitIncorrectPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=phone] input").setValue("8-993-44-11-00");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldNotSubmitWithoutAgreement() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Вася Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79934411001");
        form.$("[type='button']").click();
        form.$(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}