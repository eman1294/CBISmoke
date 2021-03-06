package com.generic.tests.FG.checkout;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.HomePage;
import com.generic.page.PayPal;
import com.generic.page.Registration;
import com.generic.page.Login;
import com.generic.setup.GlobalVariables;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.RandomUtilities;

public class PayPalValidation extends SelTestCase {

	public static void validate(String userType, int productsCount, LinkedHashMap<String, String> userDetalis,
			LinkedHashMap<String, String> paymentDetails) throws Exception {
		
		String orderSubTotal;
		String orderTax;
		String orderShipping;

		String fName = "FirstVisa";
		String lName = "LastVisa";
		String userMail = RandomUtilities.getRandomEmail();
		String userPassword = "TestITG226";

		
		// Perform login
		if (userType.contains("registered")) {

			Registration.registerFreshUser(userMail, userPassword, fName, lName);
		}

		// Add products to cart
		CheckOut.addRandomProductTocart(productsCount);
		
		Thread.sleep(2000);

		// Navigating to Cart by URL
		CheckOut.navigatetoCart();
		
		Thread.sleep(3000);
		
		Cart.closeGWPIfExsist();

		Thread.sleep(3000);

		sassert().assertTrue(Cart.checkAddedItemTotalPriceDisplay(), "Cart page is not displayed");

		Cart.paypalBtnClick();
		String main = null;
		if (SelTestCase.isDesktop())
			main = CheckOut.paymentInnformation.switchToPayPalWindow();
		Thread.sleep(1000);
		if (PayPal.isPayPalModelDisplayed()) {
			String PayPalEmail = paymentDetails.get(CheckOut.paymentInnformation.keys.number);
			String PayPalPassword = paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC);
			PayPal.signIn(PayPalEmail, PayPalPassword);
			sassert().assertTrue(PayPal.isPayPalShipToPageDisplayed(),
					"(PayPAl Ship to) page is not displayed");
			
			Thread.sleep(6000);

			if (userType.contains("registered")) {
				PayPal.clickOnContinueRegistered();
				PayPal.clickConfirmRegistered();
			}

			else {
				PayPal.clickOnContinue();

			}
			
			Thread.sleep(4500);

			if (SelTestCase.isDesktop())
				CheckOut.paymentInnformation.switchBackToMainWindow(main);

			Thread.sleep(5000);
			CheckOut.clickCheckouStepFourConfirmationButton();

			if (CheckOut.isPaymentPageSelectedAndPayPalSelected()) {
				// Saving tax and shipping costs to compare them in the confirmation page
				orderShipping = CheckOut.getShippingCosts();
				orderTax = CheckOut.getTaxCosts(GlobalVariables.FG_TAX_CART);
				orderSubTotal = CheckOut.getSubTotal();

				logs.debug(MessageFormat.format(LoggingMsg.SEL_TEXT, "Shippping cost is: " + orderShipping
						+ " ---- Tax cost is:" + orderTax + " ---- Subtotal is:" + orderSubTotal));
				
				
				CheckOut.clickPayPalOrderSubmit();
				Thread.sleep(3000);

				PayPal.closePayPalSubmitRegestration();
				
				Thread.sleep(3500);
				
				CheckOut.closePromotionalModal();

				Thread.sleep(2000);
				
				CheckOut.checkOrderValues(productsCount,orderShipping, orderTax,orderSubTotal );

				CheckOut.printOrderIDtoLogs();
			}
		}

	}

}