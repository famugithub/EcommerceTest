package stepDefinitions.uiStepDefinitions;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pages.TrendyolPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.util.ArrayList;
import java.util.List;

public class US01StepDefs {
    public ReusableMethods reusableMethods;

    TrendyolPage locatePage = new TrendyolPage();
    JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

    WebDriver driver = Driver.getDriver();

    @Given("Kullanici bir e-ticaret sitesini ziyaret eder")
    public void kullanicieticaretSayfasinaGider() {
        try {
            ReusableMethods.bekle(1);
            driver.get(ConfigReader.getProperty("siteanasayfa"));
            ReusableMethods.bekle(3);
        } catch (Exception e) {
            ReusableMethods.bekle(1);
            driver.get(ConfigReader.getProperty("siteanasayfa"));
            ReusableMethods.bekle(3);
        }
        driver.navigate().refresh();
        ReusableMethods.bekle(2);
    }

    @And("Kullanici giris islemi yapilir")
    public void kullanici_giris_islemi_yapilir() {
        ReusableMethods.bekle(1);
        try {
            locatePage.girisYapButton.click();
            ReusableMethods.bekle(3);
        } catch (Exception e) {
            driver.close();
            ReusableMethods.bekle(3);
            driver.get(ConfigReader.getProperty("siteanasayfa"));
            locatePage.girisYapButton.click();
        }
        locatePage.emailTextBox.click();
        locatePage.emailTextBox.sendKeys(ConfigReader.getProperty("email"), Keys.TAB);
        locatePage.passwordTextBox.sendKeys(ConfigReader.getProperty("password"), Keys.ENTER);
    }


    @And("Kullanici, {string} aramasi yapar")
    public void kullaniciAramasiYapar(String urun) {
        ReusableMethods.bekle(2);
        locatePage.searchBox.click();
        locatePage.searchBox.sendKeys(urun, Keys.ENTER);
        ReusableMethods.bekle(2);
    }

    @And("Arama sonuclarinda fiyat araligi olarak {int} – {int} TL filtrelemesi yapilir")
    public void aramaSonuclarindaFiyatAraligiOlarakTLFiltrelemesiYapilir(int minPrice, int maxPrice) {
        locatePage.priceFilter.click();
        locatePage.priceFilterMin.sendKeys(String.valueOf(minPrice), Keys.TAB, String.valueOf(maxPrice), Keys.TAB, Keys.ENTER);
        ReusableMethods.bekle(2);
    }

    @And("Filtrelenen sonuclarda en alt satirdan rastgele bir urun secilir ve urun detayina gidilir")
    public void filtrelenenSonuclardaEnAltSatirdanRastgeleBirUrunSecilirVeUrunDetayinaGidilir() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
        int retries = 0;
        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                retries++;
                if (retries > 2) break; // 2 kez üst üste yükleme yoksa çık
            } else {
                retries = 0;
                lastHeight = newHeight;
            }
        }
        String originalWindow = driver.getWindowHandle();
        List<WebElement> list = locatePage.products;
        if (list.size() > 0) {
            WebElement endProduct = list.get(list.size() - 1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", endProduct);
            endProduct.click();
            ReusableMethods.bekle(1);
        } else {
            throw new RuntimeException("Product not found");
        }
        List<String> allWindowHandles = new ArrayList<String>(Driver.getDriver().getWindowHandles());
        driver.switchTo().window(allWindowHandles.get(1));
        ReusableMethods.bekle(1);

        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth() / 2;
        int y = size.getHeight() / 2;

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(arguments[0], arguments[1]);", x, y);

        Actions actions = new Actions(driver);
        actions.moveByOffset(x, y).click().perform();
        actions.moveByOffset(-x, -y).perform();

    }

    @And("Urun detayinda en düsük puanli saticinin urunu sepete eklenir")
    public void urunDetayindaEnDüsükPuanliSaticininUrunuSepeteEklenir() {
        ReusableMethods.bekle(1);
        try {
            Actions actions = new Actions(driver);
            List<WebElement> foundOtherSellers = null;
            boolean isFound = false;
            for (int i = 0; i < 50; i++) {
                foundOtherSellers = locatePage.otherSellers;
                if (foundOtherSellers != null && !foundOtherSellers.isEmpty()) {
                    isFound = true;
                    List<WebElement> sellerInfoList = locatePage.sellerInfo;
                    long minPoint = Long.MAX_VALUE;
                    WebElement lowestPointElement = null;

                    for (WebElement sellerIn : sellerInfoList) {
                        try {
                            String sellerName = sellerIn.findElement(By.cssSelector("div.seller-container")).getText().trim();
                            String pointText = sellerIn.findElement(By.cssSelector("div.sl-pn")).getText().replaceAll("\\D+", "");
                            long sellerPoint = Long.parseLong(pointText);
                            if (sellerPoint < minPoint) {
                                minPoint = sellerPoint;
                                lowestPointElement = sellerIn;
                            }
                        } catch (Exception e) {
                            System.out.println("Hata oluştu: " + e.getMessage());
                        }
                    }

                    if (lowestPointElement != null) {
                        lowestPointElement.click();
                    }
                    Thread.sleep(1500);
                    locatePage.sepeteEkleButton.click();
                    Thread.sleep(500);
                    break;
                }
//

                actions.sendKeys(Keys.PAGE_DOWN).perform();
                ReusableMethods.bekle(1);
            }


            if (!isFound) {
                actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).perform();
                ReusableMethods.bekle(1);
                locatePage.sepeteEkleButton.click();
                ReusableMethods.bekle(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @And("Urunun sepete dogru eklendigi kontrol edilir")
    public void urununSepeteDogruEklendigiKontrolEdilir() {

        String expectedProductStatus = "Ürün Sepete Eklendi!";
        String actualProductStatus = locatePage.productStatusText.getText();
        Assert.assertEquals(expectedProductStatus, actualProductStatus);
    }

}
