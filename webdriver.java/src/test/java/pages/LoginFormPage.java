package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;

public class LoginFormPage extends BasePage {

    public LoginFormPage(WebDriver navegador) {
        super(navegador);
    }

    //Abordagem estrutural usa um public para cada step
    public LoginFormPage typeLogin(String login){
        navegador.findElement(By.id("signinbox")).findElement(By.name("login")).sendKeys(login);
        return this;
    }

    public LoginFormPage typePassword(String password){
        navegador.findElement(By.id("signinbox")).findElement(By.name("password")).sendKeys(password);
        return this;
    }

    public HomePage clickSignIn(){
        navegador.findElement(By.linkText("SIGN IN")).click();
        return new HomePage(navegador);

    }

    //Abordagem funcional
    public HomePage doLogin(String login, String password){
        navegador.findElement(By.id("signinbox")).findElement(By.name("login")).sendKeys(login);
        navegador.findElement(By.id("signinbox")).findElement(By.name("password")).sendKeys(password);
        navegador.findElement(By.linkText("SIGN IN")).click();
        return new HomePage(navegador);
    }

}

