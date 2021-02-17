package Testes;

import static org.junit.Assert.*;
/* Utilizando o método static e o .* no final indica que todos os métodos Assert serão estáticos, portanto nao
é preciso declarar o Assert no teste. */
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTest.csv")

public class InformacoesUsuarioTest {
    private WebDriver navegador; //declarar a variavel para todos os testes, before, after

    @Rule
    public TestName test = new TestName();


    @Before
    public void setUp(){
        navegador = Web.createChrome();

        //Clicar no link "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        //Identificando o campo
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo com o name "login" que esta dentro do form "signinbox" o texto "julio0001"0
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        //Digitar no campo com o name "password" que esta dentro do form "signinbox" o texto "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        //Clicar no elemento "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar no link que possue a class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar em um link que possui o texto "MORE DATA ABAOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();

    }


    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo,
                                                             @Param(name="contato")String contato,
                                                             @Param(name="mensagem")String mensagemEsperada){

        //clicar no botao "//button[@data-target="addmoredata"]"
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        // Identificar a popup com o id "addmoredata"
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //na combo de name "type" escolher a opção "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo de name "contact" digitar o telefone
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        //clicar no link de text "SAVE"
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        // na mensagem de id "toast-container" validar que o texto é "Your contact has been added!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada, mensagem);

    }

    @Test
    public void removerContato() {
    /*      loop para deletar todos os contatos
          for (int i=0; i<500; i++ ) {
            navegador.findElement(By.linkText("delete")).click();
            Alert alert = navegador.switchTo().alert();
            alert.accept();
            Thread.sleep(2000);
        }*/

        //clicar no elemento xpath
        navegador.findElement(By.xpath("//span[text()=\"+5511999990000\"]/following-sibling::a")).click();
        //confirmar o alert
        navegador.switchTo().alert().accept();
        // validar a mensagem
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String caminhoScreenshot = "C:\\Users\\Vinicius Olveira\\Pictures\\Selenium\\"
                + Generator.dataHoraPrint() + test.getMethodName()+ ".png";
        Screenshot.tirar(navegador,caminhoScreenshot );

        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        navegador.findElement(By.linkText("Logout")).click();
    }


    @After
    public void tearDown(){
        //Fechar o browser
       navegador.quit();
    }

}
