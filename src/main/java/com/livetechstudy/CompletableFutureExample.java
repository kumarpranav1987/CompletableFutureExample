package com.livetechstudy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//https://gist.github.com/kumarpranav1987/1303e4baa4ca3df009ac59755eaa91ce
public class CompletableFutureExample {
	private static final Logger logger = LoggerFactory.getLogger(CompletableFutureExample.class);

	public static void main(String[] args){
		logger.info("Main Started");
		Shop s = new Shop();
		ExecutorService es = Executors.newCachedThreadPool();
		Future<QuotedPricePrice> quotedPrice = es.submit(new Callable<QuotedPricePrice>() {
			@Override
			public QuotedPricePrice call() throws Exception {
				return s.getPrice("XYZ");
			}
		});
		someOtherWork();
		try {
			logger.info(quotedPrice.get().toString());//This is blocking
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		es.shutdown();
	}

	private static void someOtherWork() {
		logger.info("In Some other work");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Shop {
	private static final Logger logger = LoggerFactory.getLogger(Shop.class);

	public QuotedPricePrice getPrice(String product) {
		logger.info("Inside getPrice Method Calculating Price Of : " + product);
		return calculatePrice(product);
	}

	private QuotedPricePrice calculatePrice(String product) {
		delay();
		return new QuotedPricePrice(product, product.length()*100);
	}

	private void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class QuotedPricePrice {
	private String product;
	private double printedPrice;

	public QuotedPricePrice(String product, double printedPrice) {
		this.product = product;
		this.printedPrice = printedPrice;
	}

	public double getPrintedPrice() {
		return printedPrice;
	}

	public void setPrintedPrice(double printedPrice) {
		this.printedPrice = printedPrice;
	}

	public String getPoduct() {
		return product;
	}

	public void setPoduct(String poduct) {
		this.product = poduct;
	}

	@Override
	public String toString() {
		return "QuotedPricePrice [poduct=" + product + ", printedPrice=" + printedPrice + "]";
	}
}
