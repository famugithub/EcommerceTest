package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class TrendyolPage {

    public TrendyolPage() {
        PageFactory.initElements(Driver.getDriver(),this);}


    @FindBy(css = ".account-nav-item.user-login-container p.link-text") public WebElement girisYapButton;
    @FindBy(linkText = "Favorilerim") public WebElement myFavorites;
    @FindBy(xpath = "//*[@id='login-email']") public WebElement emailTextBox;
    @FindBy(xpath = "//*[@id='login-password-input']") public WebElement passwordTextBox;
    @FindBy(className = "V8wbcUhU") public WebElement searchBox;
    @FindBy(xpath = "//*[@class='fltrs-wrppr hide-fltrs'][4]") public WebElement priceFilter;
    @FindBy(xpath = "//*[@class='fltr-srch-prc-rng-input min']") public WebElement priceFilterMin;
    @FindBy(xpath = "//*[@class='fltr-srch-prc-rng-input max']") public WebElement priceFilterMax;
    @FindBy(xpath = "//*[@class='p-card-wrppr with-campaign-view']") public List<WebElement> products;
    @FindBy(linkText = "Anladım") public WebElement gotItButton;
    @FindBy(xpath = "//*[@class='pr-mc-w gnr-cnt-br']") public List<WebElement> otherSellers;
    @FindBy(className = "add-to-basket-button-text") public WebElement sepeteEkleButton;
    @FindBy(xpath = "//h1[@class='pr-new-br']") public WebElement productTitle;
    @FindBy(xpath = "//span[@class='product-preview-status-text']") public WebElement productStatusText;
    @FindBy(css = "//*[@class='sl-pn']") public List<WebElement> sellersPoint;
    @FindBy(css = "div.pr-mb-mn") public List<WebElement> sellerInfo;




}