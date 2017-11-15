package com.generic.page;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.generic.selector.PDPSelectors;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

public class PDP extends SelTestCase {
    private static final String DOC_URL = getCONFIG().getProperty("testSiteName");
    static List<String> subStrArr = new ArrayList<String>();
	static List<String> valuesArr = new ArrayList<String>();
	
   public static void addProductsToCart(String url, String color, String size, String qty) throws InterruptedException, IOException
   {
	   getDriver().get(url);
	   getCurrentFunctionName(true);
	   if(!color.equals(""))
		   selectcolor(color);
	   
	   if (!size.equals(""))
		   selectsize(size);
	   
	   defineQty(qty);
	   clickAddToCartBtn();
	   clickcheckoutBtnCartPopup();
	   getCurrentFunctionName(false);
   }

private static String getPrice() throws InterruptedException, IOException {
	getCurrentFunctionName(true);
	subStrArr.add(PDPSelectors.price);
	valuesArr.add("");
	SelectorUtil.initializeSelectorsAndDoActions(subStrArr,valuesArr);
	getCurrentFunctionName(false);
	return SelectorUtil.textValue;
}

private static void clickcheckoutBtnCartPopup() throws InterruptedException, IOException {
	getCurrentFunctionName(true);
	subStrArr.add(PDPSelectors.cart_popup);
	valuesArr.add("");
	SelectorUtil.initializeSelectorsAndDoActions(subStrArr,valuesArr);
	getCurrentFunctionName(false);
}

private static void clickAddToCartBtn() throws InterruptedException, IOException {
	getCurrentFunctionName(true);
	subStrArr.add(PDPSelectors.addToCartBtn);
	valuesArr.add("");
	SelectorUtil.initializeSelectorsAndDoActions(subStrArr,valuesArr);
	getCurrentFunctionName(false);
}

private static void defineQty(String qty) throws InterruptedException, IOException {
	getCurrentFunctionName(true);
	subStrArr.add(PDPSelectors.qty);
	valuesArr.add(qty);
	SelectorUtil.initializeSelectorsAndDoActions(subStrArr,valuesArr);
	getCurrentFunctionName(false);
}

private static void selectsize(String size) throws InterruptedException, IOException {
	getCurrentFunctionName(true);
	subStrArr.add(PDPSelectors.size);
	valuesArr.add(size);
	SelectorUtil.initializeSelectorsAndDoActions(subStrArr,valuesArr);
	getCurrentFunctionName(false);
}

private static void selectcolor(String color) throws InterruptedException, IOException {
	getCurrentFunctionName(true);
	subStrArr.add(color);
	valuesArr.add("");
	SelectorUtil.initializeSelectorsAndDoActions(subStrArr,valuesArr);
	getCurrentFunctionName(false);
}
   
    
    
}
