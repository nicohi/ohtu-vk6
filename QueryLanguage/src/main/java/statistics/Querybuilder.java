package statistics;

import statistics.matcher.All;
import statistics.matcher.And;
import statistics.matcher.HasAtLeast;
import statistics.matcher.HasFewerThan;
import statistics.matcher.Matcher;
import statistics.matcher.Or;
import statistics.matcher.PlaysIn;

public class Querybuilder {

	Matcher m;

	public Querybuilder() {
		this.m = new All();
	}

	Matcher build() {
		return m;
	}

	Querybuilder playsIn(String t) {
		m = new And(m, new PlaysIn(t));
		return this;
	}

	Querybuilder hasAtLeast(int i, String s) {
		m = new And(m, new HasAtLeast(i, s));
		return this;
	}

	Querybuilder hasFewerThan(int i, String s) {
		m = new And(m, new HasFewerThan(i, s));
		return this;
	}

	Querybuilder oneOf(Matcher ... ms) {
		m = new Or(ms);
		return this;
	}
	

}
