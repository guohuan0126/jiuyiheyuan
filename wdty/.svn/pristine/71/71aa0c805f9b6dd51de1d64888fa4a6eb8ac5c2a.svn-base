package com.test01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** 根据概率获取奖品的测试类 */
public class TestRandom {

    public static void main(String[] args) {
	TestRandom tr = new TestRandom();
	// 用List随机
	long begin = System.currentTimeMillis();
	for (int i = 0; i < 100; i++) {
	    tr.getGumByList();
	}
	System.out.println("list使用时间：" + (System.currentTimeMillis() - begin));
	// 用set随机
	long begin1 = System.currentTimeMillis();
	for (int i = 0; i < 100; i++) {
	    tr.getGumBySet();
	}
	System.out.println("set使用时间：" + (System.currentTimeMillis() - begin1));
    }

    /* Set 获取 */
    void getGumBySet() {
	int random = new Random().nextInt(100);
	int prizeRate = 0;// 中奖率
	Iterator<Gem> it = initSetGems().iterator();
	while (it.hasNext()) {
	    Gem gem = it.next();
	    prizeRate += gem.getPriority();
	    if (random < prizeRate) {
		System.out.println("奖品为：" + gem.getName() + "  概率："
			+ gem.getPriority() + " 随机数random：" + random
			+ " 概率基数prizeRate：" + prizeRate);
		break;
	    }
	}
    }

    /** List 获取 */
    void getGumByList() {
	int random = new Random().nextInt(100);
	int prizeRate = 0;// 中奖率
	Iterator<Gem> it = initGems().iterator();
	while (it.hasNext()) {
	    Gem gem = it.next();
	    prizeRate += gem.getPriority();
	    if (random < prizeRate) {
		System.out.println("奖品为：" + gem.getName() + "  概率："
			+ gem.getPriority() + " 随机数random：" + random
			+ " 概率基数prizeRate：" + prizeRate);
		break;
	    }
	}
    }

    /** 初始化list */
    List<Gem> initGems() {
	List<Gem> gums = new ArrayList<Gem>();
	gums.add(new Gem("20", 20));
	gums.add(new Gem("7", 7));
	gums.add(new Gem("30", 30));
	gums.add(new Gem("24", 40));
	gums.add(new Gem("3", 3));
	return gums;
    }

    /** 初始化set */
    Set<Gem> initSetGems() {
	Set<Gem> gums = new HashSet<Gem>();
	gums.add(new Gem("20", 20));
	gums.add(new Gem("7", 7));
	gums.add(new Gem("30", 30));
	gums.add(new Gem("24", 40));
	gums.add(new Gem("3", 3));
	return gums;
    }

    /**奖品类*/
    class Gem {
	/** 奖品名称 */
	private String name;
	/** 奖品概率 */
	private int priority;

	public Gem() {
	    super();
	}

	public Gem(String name, int priority) {
	    super();
	    this.name = name;
	    this.priority = priority;
	}

	@Override
	public String toString() {
	    return "Gum [name=" + name + ", priority=" + priority + "]";
	}
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	public int getPriority() {
	    return priority;
	}
	public void setPriority(int priority) {
	    this.priority = priority;
	}

    }
}
