//Projeto desenvolvido utilizando Page Objects

package Testes;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import suporte.Web;

import static org.junit.Assert.*;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioPageObjectsTest.csv")
public class InformacoesUsuariosPOTest {
    private WebDriver navegador;

    @Before
    public void setUp(){
        navegador = Web.createChrome();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="login")String login,
                                                             @Param(name="senha")String senha,
                                                             @Param(name="tipo")String tipo,
                                                             @Param(name="contato")String contato,
                                                             @Param(name="mensagem")String mensagem) {
        String textoToast = new LoginPage(navegador)
                            .clickSignIn()
                            .doLogin(login, senha)
                            .clickme()
                            .clickMoreData()
                            .clickaAddMoreData()
                            .addContact(tipo, contato)
                            .capturarTextoToast();
        assertEquals(mensagem, textoToast);
    }

    @After
    public void tearDown(){
        navegador.quit();
    }
}
