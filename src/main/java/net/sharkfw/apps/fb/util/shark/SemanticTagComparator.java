package net.sharkfw.apps.fb.util.shark;

import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkCSAlgebra;

import java.util.Comparator;

/**
 * Helper class for compare two SemanticTags in order to
 * sort Semantic Tags by the {Collection#sort()} method.
 */
public class SemanticTagComparator implements Comparator<SemanticTag> {

	private static final Comparator<SemanticTag> BY_SI_COMPARATOR = (t1, t2) -> {
		if (SharkCSAlgebra.identical(t1, t2)) return 0;

		int compareResult = 0;
		String[] si_t1 = t1.getSI();
		String[] si_t2 = t2.getSI();

		for (int i = 0; i < Math.min(si_t1.length, si_t2.length) && compareResult == 0; i++) {
			compareResult = si_t1[i].compareTo(si_t2[i]);
		}

		return compareResult;

	};

	private static final Comparator<SemanticTag> BY_NAME_COMPARATOR = (t1, t2) -> {
		return t1.getName().compareTo(t2.getName());
	};

	private Comparator<SemanticTag> concreteComparator;

	private SemanticTagComparator(Comparator<SemanticTag> comparator) {
		this.concreteComparator = comparator;
	}

	@Override
	public int compare(SemanticTag tag1, SemanticTag tag2) {
		return concreteComparator.compare(tag1, tag2);
	}

	/**
	 * Creates a Comparator for comparing two SemanticTags by it's si's.
	 * lexicographically.
	 *
	 * @return the by Si comparator.
	 */
	public static SemanticTagComparator createBySIComparator() {
		return new SemanticTagComparator(BY_SI_COMPARATOR);
	}

	/**
	 * Creates a Comparator for comparing two SemanticTags by it's names.
	 * lexicographically.
	 *
	 * @return the by Name comparator.
	 */
	public static SemanticTagComparator createByNameComperator() {
		return new SemanticTagComparator(BY_NAME_COMPARATOR);
	}


}
