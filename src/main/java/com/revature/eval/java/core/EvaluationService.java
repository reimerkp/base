package com.revature.eval.java.core;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EvaluationService {

	/**
	 * 1. Without using the StringBuilder or StringBuffer class, write a method that
	 * reverses a String. Example: reverse("example"); -> "elpmaxe"
	 * 
	 * @param string
	 * @return reverse
	 */
	public String reverse(String string) {
		String reverse = "";
		for (int i = string.length() - 1; i >= 0; i--) {
			reverse += string.charAt(i);
		}
		return reverse;
	}

	/**
	 * 2. Convert a phrase to its acronym. Techies love their TLA (Three Letter
	 * Acronyms)! Help generate some jargon by writing a program that converts a
	 * long name like Portable Network Graphics to its acronym (PNG).
	 * 
	 * @param phrase
	 * @return
	 */
	public String acronym(String phrase) {
		phrase = phrase.toUpperCase();
		String[] words = phrase.split("[,\\s-]+");
		String ans = "";
		for (String s : words)
			ans += s.charAt(0);
		return ans;
	}

	/**
	 * 3. Determine if a triangle is equilateral, isosceles, or scalene. An
	 * equilateral triangle has all three sides the same length. An isosceles
	 * triangle has at least two sides the same length. (It is sometimes specified
	 * as having exactly two sides the same length, but for the purposes of this
	 * exercise we'll say at least two.) A scalene triangle has all sides of
	 * different lengths.
	 *
	 */
	static class Triangle {
		private double sideOne;
		private double sideTwo;
		private double sideThree;

		public Triangle() {
			super();
		}

		public Triangle(double sideOne, double sideTwo, double sideThree) {
			this();
			this.sideOne = sideOne;
			this.sideTwo = sideTwo;
			this.sideThree = sideThree;
		}

		public double getSideOne() {
			return sideOne;
		}

		public void setSideOne(double sideOne) {
			this.sideOne = sideOne;
		}

		public double getSideTwo() {
			return sideTwo;
		}

		public void setSideTwo(double sideTwo) {
			this.sideTwo = sideTwo;
		}

		public double getSideThree() {
			return sideThree;
		}

		public void setSideThree(double sideThree) {
			this.sideThree = sideThree;
		}

		public boolean isEquilateral() {

			if (this.sideOne == this.sideTwo && this.sideOne == this.sideThree) {
				return true;
			}
			return false;
		}

		public boolean isIsosceles() {
			if (this.sideOne == this.sideTwo || this.sideOne == this.sideThree || this.sideTwo == this.sideThree) {
				return true;
			}
			return false;
		}

		public boolean isScalene() {
			if (this.sideOne == this.sideTwo || this.sideOne == this.sideThree || this.sideTwo == this.sideThree) {
				return false;
			}
			return true;
		}

	}

	/**
	 * 4. Given a word, compute the scrabble score for that word.
	 * 
	 * --Letter Values-- Letter Value A, E, I, O, U, L, N, R, S, T = 1; D, G = 2; B,
	 * C, M, P = 3; F, H, V, W, Y = 4; K = 5; J, X = 8; Q, Z = 10; Examples
	 * "cabbage" should be scored as worth 14 points:
	 * 
	 * 3 points for C, 1 point for A, twice 3 points for B, twice 2 points for G, 1
	 * point for E And to total:
	 * 
	 * 3 + 2*1 + 2*3 + 2 + 1 = 3 + 2 + 6 + 3 = 5 + 9 = 14
	 * 
	 * @param string
	 * @return
	 */
	public int getScrabbleScore(String string) {
		int score = 0;
		string = string.toLowerCase();
		char[] chars = string.toCharArray();
		for (char x : chars) {
			switch (x) {
			case 'a':
			case 'e':
			case 'i':
			case 'l':
			case 'n':
			case 'o':
			case 'r':
			case 's':
			case 't':
			case 'u':
				score += 1;
				break;
			case 'd':
			case 'g':
				score += 2;
				break;
			case 'b':
			case 'c':
			case 'm':
			case 'p':
				score += 3;
				break;
			case 'f':
			case 'h':
			case 'v':
			case 'w':
			case 'y':
				score += 4;
				break;
			case 'k':
				score += 5;
				break;
			case 'j':
			case 'x':
				score += 8;
				break;
			case 'q':
			case 'z':
				score += 10;
				break;
			case ' ':
				break;
			default:
				System.out.println("Invalid Character");
			}
		}
		return score;

	}

	/**
	 * 5. Clean up user-entered phone numbers so that they can be sent SMS messages.
	 * 
	 * The North American Numbering Plan (NANP) is a telephone numbering system used
	 * by many countries in North America like the United States, Canada or Bermuda.
	 * All NANP-countries share the same international country code: 1.
	 * 
	 * NANP numbers are ten-digit numbers consisting of a three-digit Numbering Plan
	 * Area code, commonly known as area code, followed by a seven-digit local
	 * number. The first three digits of the local number represent the exchange
	 * code, followed by the unique four-digit number which is the subscriber
	 * number.
	 * 
	 * The format is usually represented as
	 * 
	 * 1 (NXX)-NXX-XXXX where N is any digit from 2 through 9 and X is any digit
	 * from 0 through 9.
	 * 
	 * Your task is to clean up differently formatted telephone numbers by removing
	 * punctuation and the country code (1) if present.
	 * 
	 * For example, the inputs
	 * 
	 * +1 (613)-995-0253 613-995-0253 1 613 995 0253 613.995.0253 should all produce
	 * the output
	 * 
	 * 6139950253
	 * 
	 * Note: As this exercise only deals with telephone numbers used in
	 * NANP-countries, only 1 is considered a valid country code.
	 */
	public String cleanPhoneNumber(String string) {
		for(char x : string.toCharArray()) {
			if(Character.isAlphabetic(x)) {
				throw new IllegalArgumentException("Can not have letters in a phone number");
			}
		}
		String[] nums = string.split("[.\\(\\)\\s-]+");
		for(String s : nums) {
			if(s.length() > 4) {
				throw new IllegalArgumentException("Invalid phone number");
			}
		}
		String[] formattedNums = new String[3];
		if (nums.length == 4) {
			for (int i = 1; i < nums.length; i++) {
				formattedNums[i - 1] = nums[i];
			}
		} else {
			for (int i = 0; i < nums.length; i++) {
				formattedNums[i] = nums[i];
			}
		}
		String ans = "";
		for (String x : formattedNums) {
			ans += x;
		}
		return ans;
	}

	/**
	 * 6. Given a phrase, count the occurrences of each word in that phrase.
	 * 
	 * For example for the input "olly olly in come free" olly: 2 in: 1 come: 1
	 * free: 1
	 * 
	 * @param string
	 * @return
	 */
	public Map<String, Integer> wordCount(String string) {
		String[] splitString = string.split("[\\s,\n]+");
		List<String> stringList = Arrays.asList(splitString);
		Map<String, Integer> ans = stringList.parallelStream()
				.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));

		return ans;
	}

	/**
	 * 7. Implement a binary search algorithm.
	 * 
	 * Searching a sorted collection is a common task. A dictionary is a sorted list
	 * of word definitions. Given a word, one can find its definition. A telephone
	 * book is a sorted list of people's names, addresses, and telephone numbers.
	 * Knowing someone's name allows one to quickly find their telephone number and
	 * address.
	 * 
	 * If the list to be searched contains more than a few items (a dozen, say) a
	 * binary search will require far fewer comparisons than a linear search, but it
	 * imposes the requirement that the list be sorted.
	 * 
	 * In computer science, a binary search or half-interval search algorithm finds
	 * the position of a specified input value (the search "key") within an array
	 * sorted by key value.
	 * 
	 * In each step, the algorithm compares the search key value with the key value
	 * of the middle element of the array.
	 * 
	 * If the keys match, then a matching element has been found and its index, or
	 * position, is returned.
	 * 
	 * Otherwise, if the search key is less than the middle element's key, then the
	 * algorithm repeats its action on the sub-array to the left of the middle
	 * element or, if the search key is greater, on the sub-array to the right.
	 * 
	 * If the remaining array to be searched is empty, then the key cannot be found
	 * in the array and a special "not found" indication is returned.
	 * 
	 * A binary search halves the number of items to check with each iteration, so
	 * locating an item (or determining its absence) takes logarithmic time. A
	 * binary search is a dichotomic divide and conquer search algorithm.
	 * 
	 */
	static class BinarySearch<T extends Comparable<T>> {
		private List<T> sortedList;

		public int indexOf(T t) {
			int low = 0;
			int high = sortedList.size() - 1;
			while (low <= high) {
				int mid = (low + high) / 2;
				T midVal = sortedList.get(mid);
				int result = t.compareTo(midVal);
				if (result < 0) {
					if (high == mid + 1) {
						high = mid;
					} else
						high = mid + 1;
				} else if (result > 0)
					low = mid + 1;
				else
					return mid;

			}
			return -1;
		}

		public BinarySearch(List<T> sortedList) {
			super();
			this.sortedList = sortedList;
		}

		public List<T> getSortedList() {
			return sortedList;
		}

		public void setSortedList(List<T> sortedList) {
			this.sortedList = sortedList;
		}

	}

	/**
	 * 8. Implement a program that translates from English to Pig Latin.
	 * 
	 * Pig Latin is a made-up children's language that's intended to be confusing.
	 * It obeys a few simple rules (below), but when it's spoken quickly it's really
	 * difficult for non-children (and non-native speakers) to understand.
	 * 
	 * Rule 1: If a word begins with a vowel sound, add an "ay" sound to the end of
	 * the word. Rule 2: If a word begins with a consonant sound, move it to the end
	 * of the word, and then add an "ay" sound to the end of the word. There are a
	 * few more rules for edge cases, and there are regional variants too.
	 * 
	 * See http://en.wikipedia.org/wiki/Pig_latin for more details.
	 * 
	 * @param string
	 * @return
	 */
	public String toPigLatin(String string) {
		String[] strs = string.split("[ ]");
		boolean check = true;
		String ans = "";
		for (String s : strs) {
			check = true;
			while (check) {
				switch (s.charAt(0)) {
				case 'a':
				case 'e':
				case 'i':
				case 'o':
				case 'u':
					if (s.charAt(1) == 'i') {
						char hold = s.charAt(0);
						s = s.substring(1, s.length());
						s += hold;
					} else {
						s += "ay";
						ans += s + " ";
						check = false;
					}
					break;
				default:
					char hold = s.charAt(0);
					s = s.substring(1, s.length());
					s += hold;
				}
			}

		}
		return ans.trim();
	}

	/**
	 * 9. An Armstrong number is a number that is the sum of its own digits each
	 * raised to the power of the number of digits.
	 * 
	 * For example:
	 * 
	 * 9 is an Armstrong number, because 9 = 9^1 = 9 10 is not an Armstrong number,
	 * because 10 != 1^2 + 0^2 = 2 153 is an Armstrong number, because: 153 = 1^3 +
	 * 5^3 + 3^3 = 1 + 125 + 27 = 153 154 is not an Armstrong number, because: 154
	 * != 1^3 + 5^3 + 4^3 = 1 + 125 + 64 = 190 Write some code to determine whether
	 * a number is an Armstrong number.
	 * 
	 * @param input
	 * @return
	 */
	public boolean isArmstrongNumber(int input) {
		ArrayList<Integer> ints = new ArrayList<>();
		int checker = 0;
		if (input < 10) {
			return true;
		} else {
			String check = Integer.toString(input);
			for (int i = 0; i < check.length(); i++) {
				ints.add(Character.getNumericValue((check.charAt(i))));
			}
			for (Integer i : ints) {
				checker += Math.pow(i, ints.size());
			}
			if (checker == input) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 10. Compute the prime factors of a given natural number.
	 * 
	 * A prime number is only evenly divisible by itself and 1.
	 * 
	 * Note that 1 is not a prime number.
	 * 
	 * @param l
	 * @return
	 */
	public List<Long> calculatePrimeFactorsOf(long l) {
		List<Long> primes = new ArrayList<Long>();
		long i;
		long copyOfInput = l;
		for (i = 2; i <= copyOfInput; i++) {
			if (copyOfInput % i == 0) {
				primes.add(i);
				copyOfInput /= i;
				i--;
			}
		}
		// TODO Write an implementation for this method declaration
		return primes;
	}

	/**
	 * 11. Create an implementation of the rotational cipher, also sometimes called
	 * the Caesar cipher.
	 * 
	 * The Caesar cipher is a simple shift cipher that relies on transposing all the
	 * letters in the alphabet using an integer key between 0 and 26. Using a key of
	 * 0 or 26 will always yield the same output due to modular arithmetic. The
	 * letter is shifted for as many values as the value of the key.
	 * 
	 * The general notation for rotational ciphers is ROT + <key>. The most commonly
	 * used rotational cipher is ROT13.
	 * 
	 * A ROT13 on the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: nopqrstuvwxyzabcdefghijklm It is
	 * stronger than the Atbash cipher because it has 27 possible keys, and 25
	 * usable keys.
	 * 
	 * Ciphertext is written out in the same formatting as the input including
	 * spaces and punctuation.
	 * 
	 * Examples: ROT5 omg gives trl ROT0 c gives c ROT26 Cool gives Cool ROT13 The
	 * quick brown fox jumps over the lazy dog. gives Gur dhvpx oebja sbk whzcf bire
	 * gur ynml qbt. ROT13 Gur dhvpx oebja sbk whzcf bire gur ynml qbt. gives The
	 * quick brown fox jumps over the lazy dog.
	 */
	static class RotationalCipher {
		private int key;

		public RotationalCipher(int key) {
			super();
			this.key = key;
		}

		public String rotate(String string) {
			StringBuffer result = new StringBuffer();

			for (int i = 0; i < string.length(); i++) {
				if (Character.isUpperCase(string.charAt(i))) {
					char ch = (char) (((int) string.charAt(i) + this.key - 65) % 26 + 65);
					result.append(ch);
				} else if (!Character.isAlphabetic(string.charAt(i))) {
					result.append(string.charAt(i));
					continue;
				} else {
					char ch = (char) (((int) string.charAt(i) + this.key - 97) % 26 + 97);
					result.append(ch);
				}
			}
			return result.toString();
		}

	}

	/**
	 * 12. Given a number n, determine what the nth prime is.
	 * 
	 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
	 * that the 6th prime is 13.
	 * 
	 * If your language provides methods in the standard library to deal with prime
	 * numbers, pretend they don't exist and implement them yourself.
	 * 
	 * @param i
	 * @return
	 */
	public int calculateNthPrime(int i) {
		if (i == 0) {
			throw new IllegalArgumentException("No such thing");
		}
		int count = 2;
		if (i == 1) {
			return 2;
		} else if (i == 2) {
			return 3;
		} else {
			for (int x = 5; x > 2; x++) {
				for (int j = x - 1; j >= 2; j--) {
					if (x % j == 0) {
						break;
					} else if (j == 2) {
						count++;
						if (count == i) {
							return x;
						}
					}
				}
			}

		}
		return -1;
	}

	/**
	 * 13 & 14. Create an implementation of the atbash cipher, an ancient encryption
	 * system created in the Middle East.
	 * 
	 * The Atbash cipher is a simple substitution cipher that relies on transposing
	 * all the letters in the alphabet such that the resulting alphabet is
	 * backwards. The first letter is replaced with the last letter, the second with
	 * the second-last, and so on.
	 * 
	 * An Atbash cipher for the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: zyxwvutsrqponmlkjihgfedcba It is a
	 * very weak cipher because it only has one possible key, and it is a simple
	 * monoalphabetic substitution cipher. However, this may not have been an issue
	 * in the cipher's time.
	 * 
	 * Ciphertext is written out in groups of fixed length, the traditional group
	 * size being 5 letters, and punctuation is excluded. This is to make it harder
	 * to guess things based on word boundaries.
	 * 
	 * Examples Encoding test gives gvhg Decoding gvhg gives test Decoding gsvjf
	 * rxpyi ldmul cqfnk hlevi gsvoz abwlt gives thequickbrownfoxjumpsoverthelazydog
	 *
	 */
	static class AtbashCipher {

		/**
		 * Question 13
		 * 
		 * @param string
		 * @return
		 */
		public static String encode(String string) {
			String cipherTxt = "";
			string = removeUnwantedChars(string.toLowerCase());
			for (char c : string.toCharArray()) {
				if (Character.isLetter(c)) {
					cipherTxt += (char) ('a' + ('z' - c));
				} else
					cipherTxt += c;
			}
			cipherTxt = getSubstrings(cipherTxt);
			return cipherTxt.trim();
		}

		private static String getSubstrings(String input) {
			String out = "";
			for (int i = 0; i < input.length(); i += 5) {
				if (i + 5 <= input.length()) {
					out += (input.substring(i, i + 5) + " ");
				} else
					out += (input.substring(i));
			}
			return out;
		}

		private static String removeUnwantedChars(String input) {
			String out = "";
			for (char c : input.toCharArray()) {
				if (Character.isLetterOrDigit(c)) {
					out += c;
				}
			}
			return out;
		}

		/**
		 * Question 14
		 * 
		 * @param string
		 * @return
		 */
		public static String decode(String string) {
			String plainTxt = "";
			string = removeUnwantedChars(string.toLowerCase());
			for (char c : string.toCharArray()) {
				if (Character.isLetter(c)) {
					plainTxt += (char) ('z' + ('a' - c));
				} else
					plainTxt += c;
			}
			return plainTxt;
		}
	}

	/**
	 * 15. The ISBN-10 verification process is used to validate book identification
	 * numbers. These normally contain dashes and look like: 3-598-21508-8
	 * 
	 * ISBN The ISBN-10 format is 9 digits (0 to 9) plus one check character (either
	 * a digit or an X only). In the case the check character is an X, this
	 * represents the value '10'. These may be communicated with or without hyphens,
	 * and can be checked for their validity by the following formula:
	 * 
	 * (x1 * 10 + x2 * 9 + x3 * 8 + x4 * 7 + x5 * 6 + x6 * 5 + x7 * 4 + x8 * 3 + x9
	 * * 2 + x10 * 1) mod 11 == 0 If the result is 0, then it is a valid ISBN-10,
	 * otherwise it is invalid.
	 * 
	 * Example Let's take the ISBN-10 3-598-21508-8. We plug it in to the formula,
	 * and get:
	 * 
	 * (3 * 10 + 5 * 9 + 9 * 8 + 8 * 7 + 2 * 6 + 1 * 5 + 5 * 4 + 0 * 3 + 8 * 2 + 8 *
	 * 1) mod 11 == 0 Since the result is 0, this proves that our ISBN is valid.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isValidIsbn(String string) {
		String[] noHyphen = string.split("[-]+");
		String noHyph = "";
		for (String s : noHyphen) {
			noHyph += s;
		}
		int check = 0;
		int track = 10;
		for (int i = 0; i < noHyph.length() - 1; i++) {
			char y = noHyph.charAt(i);
			if (Character.isAlphabetic(y))
				return false;
			check += Character.getNumericValue(y) * track;
			track--;
		}
		char isX = noHyph.charAt(noHyph.length() - 1);
		if (Character.isAlphabetic(isX) && isX == 'X') {
			check += track * 10;
		} else if (Character.isDigit(isX)) {
			check += track * Character.getNumericValue(isX);
		} else {
			return false;
		}
		if (check % 11 == 0)
			return true;
		return false;
	}

	/**
	 * 16. Determine if a sentence is a pangram. A pangram (Greek: παν γράμμα, pan
	 * gramma, "every letter") is a sentence using every letter of the alphabet at
	 * least once. The best known English pangram is:
	 * 
	 * The quick brown fox jumps over the lazy dog.
	 * 
	 * The alphabet used consists of ASCII letters a to z, inclusive, and is case
	 * insensitive. Input will not contain non-ASCII symbols.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isPangram(String string) {
		int index = 0;
		boolean[] mark = new boolean[26];
		// Traverse all characters
		for (int i = 0; i < string.length(); i++) {
			if ('a' <= string.charAt(i) && string.charAt(i) <= 'z')

				index = string.charAt(i) - 'a';

			// Mark current character
			mark[index] = true;
		}
		for (int i = 0; i < mark.length - 1; i++) {
			if (!mark[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 17. Calculate the moment when someone has lived for 10^9 seconds.
	 * 
	 * A gigasecond is 109 (1,000,000,000) seconds.
	 * 
	 * @param given
	 * @return
	 */
	public Temporal getGigasecondDate(Temporal given) {
		LocalDate date;
		LocalDateTime dateTime;
		if (given.getClass().getName().equals("java.time.LocalDateTime")) {
			dateTime = LocalDateTime.from(given);
		} else {
			date = LocalDate.from(given);
			dateTime = date.atStartOfDay();
		}
		dateTime = dateTime.plus((long) Math.pow(10, 9), ChronoUnit.SECONDS);
		return dateTime;
	}

	/**
	 * 18. Given a number, find the sum of all the unique multiples of particular
	 * numbers up to but not including that number.
	 * 
	 * If we list all the natural numbers below 20 that are multiples of 3 or 5, we
	 * get 3, 5, 6, 9, 10, 12, 15, and 18.
	 * 
	 * The sum of these multiples is 78.
	 * 
	 * @param i
	 * @param set
	 * @return
	 */
	public int getSumOfMultiples(int i, int[] set) {
		ArrayList<Integer> multiples = new ArrayList<>();
		for (int x = 1; x < i; x++) {
			for (int y : set) {
				int check = y * x;
				if (check < i) {
					if (!multiples.contains(check))
						multiples.add(check);
				}
			}
		}
		int sum = 0;
		for (int z : multiples) {
			sum += z;
		}
		return sum;
	}

	/**
	 * 19. Given a number determine whether or not it is valid per the Luhn formula.
	 * 
	 * The Luhn algorithm is a simple checksum formula used to validate a variety of
	 * identification numbers, such as credit card numbers and Canadian Social
	 * Insurance Numbers.
	 * 
	 * The task is to check if a given string is valid.
	 * 
	 * Validating a Number Strings of length 1 or less are not valid. Spaces are
	 * allowed in the input, but they should be stripped before checking. All other
	 * non-digit characters are disallowed.
	 * 
	 * Example 1: valid credit card number 1 4539 1488 0343 6467 The first step of
	 * the Luhn algorithm is to double every second digit, starting from the right.
	 * We will be doubling
	 * 
	 * 4_3_ 1_8_ 0_4_ 6_6_ If doubling the number results in a number greater than 9
	 * then subtract 9 from the product. The results of our doubling:
	 * 
	 * 8569 2478 0383 3437 Then sum all of the digits:
	 * 
	 * 8+5+6+9+2+4+7+8+0+3+8+3+3+4+3+7 = 80 If the sum is evenly divisible by 10,
	 * then the number is valid. This number is valid!
	 * 
	 * Example 2: invalid credit card number 1 8273 1232 7352 0569 Double the second
	 * digits, starting from the right
	 * 
	 * 7253 2262 5312 0539 Sum the digits
	 * 
	 * 7+2+5+3+2+2+6+2+5+3+1+2+0+5+3+9 = 57 57 is not evenly divisible by 10, so
	 * this number is not valid.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isLuhnValid(String string) {
		int check = 0;
		for (int i = 0; i < string.length() - 1; i++) {
			if (Character.isAlphabetic(string.charAt(i)))
				return false;
			if (Character.isWhitespace(string.charAt(i))) {
				continue;
			} else if (check % 2 == 0) {
				check++;
				continue;
			} else {
				int doub = Character.getNumericValue(string.charAt(i));
				doub *= 2;
				if (doub > 9)
					doub -= 9;
				String doubString = Integer.toString(doub);
				string = string.substring(0, i) + doubString + string.substring(i + 1);
				check++;
			}
		}
		int answer = 0;
		for (char c : string.toCharArray()) {
			if (Character.isWhitespace(c)) {
				continue;
			}
			answer += Character.getNumericValue(c);
		}
		if (answer % 10 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 20. Parse and evaluate simple math word problems returning the answer as an
	 * integer.
	 * 
	 * Add two numbers together.
	 * 
	 * What is 5 plus 13?
	 * 
	 * 18
	 * 
	 * Now, perform the other three operations.
	 * 
	 * What is 7 minus 5?
	 * 
	 * 2
	 * 
	 * What is 6 multiplied by 4?
	 * 
	 * 24
	 * 
	 * What is 25 divided by 5?
	 * 
	 * 5
	 * 
	 * @param string
	 * @return
	 */
	public int solveWordProblem(String string) {
		String[] separated = string.split(" ");
		int fNum = Integer.parseInt(separated[2]);
		int sNum = 0;
		String operation = separated[3];
		switch (operation) {
		case "plus":
		case "minus":
			sNum = Integer.parseInt(separated[4].substring(0, separated[4].length()-1));
			if (operation.equals("plus"))
				return fNum + sNum;
			else
				return fNum - sNum;
		case "multiplied":
		case "divided":
			sNum = Integer.parseInt(separated[5].substring(0, separated[5].length() - 1));
			if (operation.equals("multiplied"))
				return fNum * sNum;
			else
				return fNum / sNum;
		}
		return 0;
	}

}
