package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.generic.selector.HomePageSelectors;
import com.generic.selector.PLPSelectors;
import com.generic.setup.Common;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.GlobalVariables;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

public class PLP extends SelTestCase {

	public static class keys {
		public static final String caseId = "caseId";
	}

	// CBI
	public static boolean searchAndVerifyResults(String SearchTerm, boolean recommendedOption) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean result;
			if (SelTestCase.getBrowserName().contains(GlobalVariables.browsers.iPad))
				disableMonetate();
			
			if (!isGH()) {
				if (isRY()) {
					if (isMobile())
						clickSearchicon();
				} else
					clickSearchicon();
			}

			typeSearch(SearchTerm);
			
//			if(isRY() && !isMobile())
//			PDP.closeSignUpModalIfDisplayed();	
			
			if (recommendedOption) {
				String productName = pickRecommendedOption();
				result = verifyPickedProduct(productName);
			} else {
				clickSearch(SearchTerm);
				result = verifySearchResultPage();
			}
			getCurrentFunctionName(false);
			return result;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	private static boolean verifySearchResultPage() throws Exception {
		try {
			getCurrentFunctionName(true);
			Thread.sleep(3000);

			getDriver().navigate().refresh();

			boolean result = true;
			result = result && verifyProductImagesDisplayed();

			sortByPriceLowToHigh();
			List<String> L2HproductsNames = getfirst3ProductsNames();

			Thread.sleep(3000);

			
			if (isGR() || isFG())
				sortByPriceHighToLow();

			else if (isGH() || isRY())
				sortByProductName();


			List<String> H2LsortedProductsNames = getfirst3ProductsNames();

			result = result && compareOperationResults(L2HproductsNames, H2LsortedProductsNames);

			Thread.sleep(3000);

			String firstProductName = getfirst3ProductsNames().get(0);
			SelectFilter();
			Thread.sleep(3000);
			String secondProductName = getfirst3ProductsNames().get(0);
			result = result && (firstProductName != secondProductName);

			logs.debug("Filters check result " + result);

			getCurrentFunctionName(false);
			return result;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean compareFilterResults(int before, int after) throws Exception {
		try {
			getCurrentFunctionName(true);

			boolean result = false;

			if (before != after)
				result = true;
			else
				result = false;

			getCurrentFunctionName(false);
			return result;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	public static boolean VerifyPLP() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean result = true;
			Thread.sleep(3000);

			result = result && verifyProductImagesDisplayed();

			logs.debug("Image cehck result " + result);

			sortByPriceLowToHighPLP();
			List<String> L2HproductsNames = getfirst3ProductsNames();

			Thread.sleep(2500);

			if (isGR() || isFG())
				sortByPriceHighToLow();

			else if (isGH() || isRY())
				sortByProductName();

			List<String> H2LsortedProductsNames = getfirst3ProductsNames();

			result = result && compareOperationResults(L2HproductsNames, H2LsortedProductsNames);

			logs.debug("Sorting result " + result);

			int productsCountBeforeFilter = getProductsCountinPLP();

			if (checkFiltersAvillability()) {
				SelectFilter();
				Thread.sleep(2000);

				result = result && compareFilterResults(productsCountBeforeFilter, getProductsCountinPLP());

				logs.debug("Filters check result " + productsCountBeforeFilter + getProductsCountinPLP() + result);

			}
			getCurrentFunctionName(false);
			return result;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	
	

	// CBI
	private static void clickOnFilterBy() throws Exception {
		try {
			getCurrentFunctionName(true);

			if (isFG())
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.FilterContainer.get());
			if (isGR()) {

				if (!isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRFilterContainer.get());
				} else
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRFilterContainer.get());

			}

			if (isGH() && isMobile()) {
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.FilterContainerGHRY.get());
			}
			if (isRY()) {
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.FilterContainerGHRY.get());

			}

			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static void selectFilterName() throws Exception {
		try {
			getCurrentFunctionName(true);

			Thread.sleep(2000);

			if (isFG()) {
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.FilterContainerContents.get(),
						"ForceAction,click");

				if (isMobile()) {
					try {
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.allCatigories.get(),
								"ForceAction,click");
					} catch (Exception e) {

						try {

							SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.allCatigories2.get(),
									"ForceAction,click");

						} catch (Exception e2) {
							List<WebElement> maxPriceField = SelectorUtil
									.getElementsList(PLPSelectors.filterPrice.get());
							maxPriceField.get(0).clear();
							maxPriceField.get(0).sendKeys("250");

						}

					}

				}

			}

			if (isGR()) {

				if (isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRFilterContainerContents.get(),
							"ForceAction,click");
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRallCatigories.get(),
							"ForceAction,click");
				} else
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRFilterContainerContents.get(),
							"ForceAction,click");
			}

			if (isGH()) {
				if (isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.FilterContainerContentsGH.get(),
							"ForceAction,click");
					try {
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.clearanceGH.get(),
								"ForceAction,click");
					} catch (Exception e) {
						List<WebElement> filters = SelectorUtil.getAllElements(PLPSelectors.colorFilterGH.get());
						filters.get(2).click();
					}

				} else {
					try {
						
						List<WebElement> filters = SelectorUtil
								.getElementsList(PLPSelectors.clearanceGH.get());
						filters.get(2).click();

					} catch (Exception e) {
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.colorFilterGH.get(),
								"ForceAction,click");

					}

				}
			}

			if (isRY()) {

				List<WebElement> filters = SelectorUtil.getAllElements((PLPSelectors.firstFilterRY.get()));
				filters.get(2).click();
			}

			getCurrentFunctionName(false);

		
		}catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	private static void SelectFilter() throws Exception {
		try {
			getCurrentFunctionName(true);
			clickOnFilterBy();
			selectFilterName();
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static boolean checkFiltersAvillability() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean state = false;

			if (isGR()) {
				try {
					state = SelectorUtil.isDisplayed(PLPSelectors.GRFilterContainer.get());
				} catch (Exception e) {
					return false;
				}

			} else if (isFG()) {
				try {
					state = SelectorUtil.isDisplayed(PLPSelectors.FilterContainer.get());
				} catch (Exception e) {
					return false;
				}
			}
			
			else if (isGH()) {
				try {
					if (isMobile())
						state = SelectorUtil.isDisplayed(PLPSelectors.FilterContainerGHRY.get());
					else
						state = SelectorUtil.isDisplayed(PLPSelectors.FilterContainerGHRYDesktop.get());

				} catch (Exception e) {
					return false;
				}
			}

			else if (isRY()) {
				try {
					state = SelectorUtil.isDisplayed(PLPSelectors.FilterContainerGHRY.get());

				} catch (Exception e) {
					return false;
				}
			}
		
					

			getCurrentFunctionName(false);
			return state;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	@SuppressWarnings("unused")
	private static int getNumberOfProducts() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<WebElement> namesWebElements = SelectorUtil.getAllElements(PLPSelectors.productsNames.get());
			int productNumbers = namesWebElements.size();
			getCurrentFunctionName(false);
			return productNumbers;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static boolean compareOperationResults(List<String> productsNames, List<String> sortedProductsNames) {
		boolean result = false;
		try {
			getCurrentFunctionName(true);
			result = !productsNames.equals(sortedProductsNames);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
		return result;
	}

	// CBI
	private static void sortByPriceLowToHigh() throws Exception {

		try {
			getCurrentFunctionName(true);

			if (isFG()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceLowToHigh.get(), "forceAction,click");
			}
			if (isGR()) {
				if (isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRMobileSorting.get(), "FFF2");
				} else {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSorting.get());
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSortingLowtoHIgh.get());

				}

			}
			if(isGH()) {
				if (isMobile())
				{
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceLowToHighGH.get(),"forceAction,click");
							
			}
			
			if(isRY()) {
				clickOnSortMenu();					

				if (isMobile()) {

					List<WebElement> sortingOptions = SelectorUtil.getAllElements(PLPSelectors.PriceLowToHighRYMobile.get());
					sortingOptions.get(2).click();
				} else {
					List<WebElement> sortingOptions = SelectorUtil.getAllElements(PLPSelectors.PriceLowToHighRYDesktop.get());
					sortingOptions.get(2).click();
				}
				
			}
			
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static void sortByPriceHighToLow() throws Exception {

		try {
			getCurrentFunctionName(true);

			if (isFG()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceHighToLow.get());
			}
			if (isGR()) {
				if (isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRMobileSorting.get(), "FFF3");
				} else {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSorting.get());
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSortingHighToLow.get());

				}

			}
			
			if (isGH()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.ProductNameGH.get());

			}
			
			
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static void sortByProductName() throws Exception {

		try {
			getCurrentFunctionName(true);

			if (isGH()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.ProductNameGH.get());

			}
			
			if(isRY()) {
				clickOnSortMenu();
				List<WebElement> sortingoptions = SelectorUtil.getAllElements(PLPSelectors.ProductNameRY.get());
				sortingoptions.get(4).click();
				
			}

			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	
	
	// CBI
	private static void sortByPriceLowToHighPLP() throws Exception {

		try {
			getCurrentFunctionName(true);

			if (isFG()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceLowToHighPLP.get());
			}
			if (isGR()) {
				if (isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRMobileSorting.get(), "FFF2");
				} else {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSorting.get());
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSortingLowtoHIgh.get());

				}

			}
			
			if (isGH()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				try {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceLowToHighPLPGH.get(),
							"forceAction,click");
				} catch (Exception e) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceLowToHighPLPGHUNBXD.get(),
							"forceAction,click");

				}
			}
			
			if(isRY()) {
				clickOnSortMenu();					
//				PDP.closeSignUpModalIfDisplayed();

				if (isMobile()) {

					List<WebElement> sortingOptions = SelectorUtil.getAllElements(PLPSelectors.PriceLowToHighRYMobile.get());
					sortingOptions.get(2).click();
				} else {
					List<WebElement> sortingOptions = SelectorUtil.getAllElements(PLPSelectors.PriceLowToHighRYDesktop.get());
					sortingOptions.get(2).click();
				}
				
			}
			
			
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static void sortByPriceHighToLowPLP() throws Exception {

		try {
			getCurrentFunctionName(true);

			if (isFG()) {
				if (isMobile()) {
					clickOnSortMenu();
				}
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.PriceHighToLowPLP.get());
			}
			if (isGR()) {
				if (isMobile()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRMobileSorting.get(), "FFF3");
				} else {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSorting.get());
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GRDeskTopSortingHighToLow.get());

				}

			}
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	//CBI
	private static void clickOnSortMenu() throws Exception {
		try {
		getCurrentFunctionName(true);
		
			if (isRY()) {
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.SortingMenuRY.get());

			} else {

				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.mobileSortingMenu.get());
			}
			
		getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	public static List<String> getfirst3ProductsNames() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> productsName = new ArrayList<String>();
			List<WebElement> namesWebElements = SelectorUtil.getAllElements(PLPSelectors.productsNames.get());
			for (int index = 0; index < namesWebElements.size() && index < 3; index++) {
				WebElement prodname = namesWebElements.get(index);
				String prodnameStr = prodname.getText();
				productsName.add(prodnameStr);
			}
			getCurrentFunctionName(false);
			return productsName;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	public static int getProductsCountinPLP() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<WebElement> namesWebElements = SelectorUtil.getAllElements(PLPSelectors.productsNames.get());
			getCurrentFunctionName(false);

			return namesWebElements.size();

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	public static boolean verifyProductImagesDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean result;
			Thread.sleep(2500);

			if (isGR())
				result = SelectorUtil.isImgLoaded(PLPSelectors.productsImagesGR.get());
			
			if (isGH() || isRY())
				result = SelectorUtil.isImgLoaded(PLPSelectors.productsImagesGH.get());
			
			else
				result = SelectorUtil.isImgLoaded(PLPSelectors.productsImages.get());

			getCurrentFunctionName(false);
			return result;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static boolean verifyPickedProduct(String productName) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean result;
			String productTitle;

			if (isRY()) {
				productTitle = PDP.getImageSrcID();
			} else {
				productTitle = PDP.getTitle();
			}
			
			result = productName.contains(productTitle);
						
			getCurrentFunctionName(false);
			return result;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	public static void clickSearch(String searchTerm) throws Exception {
		try {
			getCurrentFunctionName(true);
			SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.searchBox.get(), searchTerm + ",pressEnter");

			if (isMobile())
				Thread.sleep(5000);

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
    
// CBI
		public static String pickRecommendedOption() throws Exception {
		String itemTitle = "";
		try {
			getCurrentFunctionName(true);
			String imgID;
			String SelectorSS;

			if (isRY()) {
	
			    SelectorSS = PLPSelectors.recommendedOptionRY.get();

			}else if(isGH()) {
				SelectorSS = PLPSelectors.GHRecommendedOption.get();
			} else {
				SelectorSS = PLPSelectors.recommendedOption.get();
			}
			
			WebElement recommendedProduct = SelectorUtil.getElement(SelectorSS);
			if (isGH() && isiPad()) {
				// The GH option didn't contains suggestion product so submit search.
				// (The unbxd redirect the site to PDP if the search for product id).
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.GHSearchButton.get());
			}else if (isRY()) {
				imgID = recommendedProduct.getAttribute("innerHTML");
				itemTitle = imgID.substring(imgID.indexOf("Ryllace") + 8, imgID.indexOf("Ryllace") + 13);
			} else {
				
				itemTitle = recommendedProduct.getText();
				logs.debug("Picked item: " + itemTitle);
				recommendedProduct.click();

				getCurrentFunctionName(false);
			}
			return itemTitle;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
  
	// CBI
	public static void typeSearch(String searchTerm) throws Exception {
		try {
			getCurrentFunctionName(true);
			SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.searchBox.get(), searchTerm);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	public static void clickSearchicon() throws Exception {
		try {
			getCurrentFunctionName(true);
			if (!isRY())
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.SearchIcon.get());
			else
				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.RYSearchIcon.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	public static String pickPLPFirstProduct() throws Exception {
		try {
			getCurrentFunctionName(true);
			String SelectorSS;
			if (isGHRY())
				SelectorSS = PLPSelectors.productsImagesGH.get();
			else if (isGR())
				SelectorSS = PLPSelectors.productsImagesGR.get();
			else
				SelectorSS = PLPSelectors.productsImages.get();
			String itemTitle = SelectorUtil.getAttrString(SelectorSS, "alt");
			SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.productName.get());
			getCurrentFunctionName(false);
			return itemTitle;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	// CBI
	public static void pickPLPRandomProduct() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<WebElement> items = new ArrayList<WebElement>();
			items = getPLPItems();
			WebElement item =  SelectorUtil.getRandomWebElement(items);
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(false)", item);
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].click()", item);
			SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.productName.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	public static List<WebElement> getPLPItems() throws Exception {
		try {
			List<WebElement> items = new ArrayList<WebElement>();
			items = SelectorUtil.getAllElements(PLPSelectors.productName.get());
			items.remove(items.size() - 1);
			return items;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	// CBI
	public static void navigateToRandomPLPMobileIpad() throws Exception {
		try {
			getCurrentFunctionName(true);
			
			// Open menu
			HomePage.openNavigationMenu();


			List<WebElement> menueItems = new ArrayList<WebElement>();
			
			if (isGH()) {
				menueItems = CLP.menueForGH();

			} else {
				menueItems = CLP.menueWithoutWhatsNew();

			}

			WebElement randomMenuElement = SelectorUtil.getRandomWebElement(menueItems);
			// Click on random menu element
			SelectorUtil.clickOnWebElement(randomMenuElement);

			List<WebElement> leafMenuItems = SelectorUtil.getAllElements(HomePageSelectors.leafMenuItems.get());

			// Select a random item from the leaf items list.
			Random rand = new Random();
			WebElement randomElement = leafMenuItems.get(rand.nextInt(leafMenuItems.size()));

			// Navigate to the selected random page.
			SelectorUtil.clickOnWebElement(randomElement);
			
			// Check if the target is CLP
			if (isCLP()) {
				// Navigate to a PLP
				Thread.sleep(2000);

				try {
					if (isFG())
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLP.get());
					else if (isGR())
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLPGR.get());
					else if (isGH())
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLPGH.get());

				} catch (Exception e) {

					if (isFG())
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLP2.get());
					else if (isGR())
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLP2GR.get());
				}
			}

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	public static void navigateToRandomPLPDesktop() throws Exception {
		try {
			getCurrentFunctionName(true);
			// Get the menu items list in first level.
			List<WebElement> menuFirstLevelElements = HomePage.getFirstLevelMenuItems();
			Random randomGenerator = new Random();
			WebElement randomElement = menuFirstLevelElements
					.get(randomGenerator.nextInt(menuFirstLevelElements.size() - 1));
			SelectorUtil.clickOnWebElement(randomElement);

			if (isCLP()) {
				// Navigate to a PLP
				if (isGH()) {
					SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLPGH.get());

				}

				else {
					try {
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLP.get());
					} catch (Exception e) {
						SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLP2.get());

					}
				}
			}
			 
			Thread.sleep(1500);

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	// CBI
	public static void navigateToRandomPLPIpadGH() throws Exception {
		try {
			getCurrentFunctionName(true);
			JavascriptExecutor js = (JavascriptExecutor)getDriver();  
			String script ="var Nodes = document.querySelectorAll (\"" + HomePageSelectors.menuItemsGH.get()+ "\");"
					+ "var targetNode = Nodes[Math.floor(Math.random()*Nodes.length)];"
					+ "if (targetNode) {"
					+ "triggerMouseEvent (targetNode, \"mousedown\");"
					+ "}"
					+ "function triggerMouseEvent (node, eventType) {"
					+ "var clickEvent = document.createEvent ('MouseEvents');"
					+ "clickEvent.initEvent (eventType, true, true);"
					+ "node.dispatchEvent (clickEvent);"
					+ "}";
			js.executeScript(script);
			
			if (isCLP()) {

				SelectorUtil.initializeSelectorsAndDoActions(PLPSelectors.navigatetoPLPGH.get());

			}
			
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	
	// CBI
	public static void navigateToRandomPLPRY() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<WebElement> menuFirstLevelElements;
			
			if (isMobile() || isiPad()) {
				SelectorUtil.initializeSelectorsAndDoActions(HomePageSelectors.shopMenuRY.get());

				if (isMobile()) { // Expand the menu options on mobile
					List<WebElement> expandIcons= SelectorUtil.getAllElements(HomePageSelectors.expandShopMenuRY.get());
					expandIcons.get(4).click();
				}
					
				menuFirstLevelElements = SelectorUtil.getAllElements(HomePageSelectors.menuItemsRY.get());
				Random randomGenerator = new Random();
				WebElement randomElement = menuFirstLevelElements
						.get(randomGenerator.nextInt(menuFirstLevelElements.size() - 1));
				SelectorUtil.clickOnWebElement(randomElement);

			} else {
				Actions actions = new Actions(getDriver());
				WebElement shopMenu = SelectorUtil.getElement(HomePageSelectors.shopMenuRY.get());
				actions.moveToElement(shopMenu).perform(); // Hover on shop menu

				menuFirstLevelElements = SelectorUtil.getAllElements(HomePageSelectors.menuItemsRY.get());
				Random randomGenerator = new Random();
				WebElement randomElement = menuFirstLevelElements
						.get(randomGenerator.nextInt(menuFirstLevelElements.size() - 1));
				SelectorUtil.clickOnWebElement(randomElement);

			}
			
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	

	// CBI

	public static boolean isCLP() throws Exception {
		getCurrentFunctionName(true);
		try {
			Thread.sleep(2500);
			return !SelectorUtil.isDisplayed(PLPSelectors.PLPIdentifier.get());
		} catch (Exception e) {
			getCurrentFunctionName(false);

			return true;
		}
	}

}
