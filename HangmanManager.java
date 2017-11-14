// Taylor Olson
// CSE 143 C with Justin Morgan
// Homework 5
// Application logic for game of hangman 
// where a player guesses multiple characters
// until they either guess the word or run out
// of chances. The evil hangman does not choose
// a target word until forced to by constantly 
// changing the set of words depending on which
// possible letter pattern contains the largest 
// number of words.  

import java.util.*;

public class HangmanManager {
   private Set<String> wordList;
   private SortedSet<Character> guesses;
   private int remainingGuesses;
   
   // itializes the state of the hangman game given a dictionary, the 
   // length of word, and max number of guesses inputed by the user
   //      @param dictionary: list of possible words of all lengths
   //      @param length: desired length of target word
   //      @param max: maximum number of guesses 
   public HangmanManager(List<String> dictionary, int length, int max) {
      if (length < 1 || max < 0)
         throw new IllegalArgumentException();  
      
      remainingGuesses = max;
      guesses = new TreeSet<Character>();
      wordList = new TreeSet<String>();
      
      for (String word : dictionary) {
         if (word.length() == length) 
            wordList.add(word);
      }      
   }
   
   // returns a set of the remaining possbile words in the dictionary
   //      @return Set<String>: list of possible words
   public Set<String> words() {
      return wordList;
   }
   
   // returns the number of guesses remaining in the game
   //      @return int: number of guesses
   public int guessesLeft() {
      return remainingGuesses;
   }
   
   // returns a sorted set of the user's letter guesses
   //      @return SortedSet<Character>: a sorted list of the user's guesses
   public SortedSet<Character> guesses() {
      return guesses;
   }
   
   // returns a string representation of possible target words taking into 
   // account the user's previous guesses
   //      return String: current pattern of the target word based on the user's guesses
   public String pattern() {
      if (wordList.isEmpty()) 
         throw new IllegalStateException();     
      return pattern(wordList.iterator().next());
   }
   
   // uses the next guess made by the user to update the set of words remaining for
   // future turns of the game, updates the number of guesses left, and returns
   // the number of times the guessed letter occurs 
   //      @param guess: character guessed by the user
   //      @return int: number of times the guessed char occurs in the new family's pattern
   public int record(char guess) {
      if (wordList.isEmpty() || remainingGuesses == 0)  
         throw new IllegalStateException();
      else if (!wordList.isEmpty() && guesses.contains(guess))
         throw new IllegalArgumentException();
      
      Map<String, Set<String>> families = new TreeMap<String, Set<String>>();
      String initialPattern = this.pattern();
      guesses.add(guess);      
      updateWordList(families);
           
      if (this.pattern().equals(initialPattern))
         remainingGuesses--;
      return countMatches(this.pattern(), guess);
   }
   
   // returns the number of times a character is in a given pattern
   //      @param pattern: String representation of a family of words
   //      @param pattern: character guessed by the user
   //      @return int: number of occurences of a char within the given pattern
   private int countMatches (String pattern, char guess) {
      int matches = 0;
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess)
            matches++;
      }
      return matches;
   }   
   
   // updates the set of possible words to the largest subset
   // word families
   //      @param families: Map of family pattern and Set of words in the family
   private void updateWordList(Map<String, Set<String>> families) {
      for (String word : wordList) {
         String currentPattern = pattern(word);      
         if (!families.containsKey(currentPattern))
            families.put(currentPattern, new TreeSet<String>());
         families.get(currentPattern).add(word);
      }
      wordList = families.get(getLargestKey(families));
   }   
   
   // returns the String key from the largest set of words inputed
   //      @param families: a Map with String keys representing sets of possible words
   //      @return String: key linked to the largest Set of possible words   
   private String getLargestKey(Map<String, Set<String>> families) {
      int maxLength = 0;
      String maxKey = "";
      for (String key : families.keySet()) {
         if (families.get(key).size() > maxLength) {
            maxLength = families.get(key).size();
            maxKey = key;
         }
      }     
      return maxKey;
   }
   
   // returns a string of a given word with the unknown characters
   // (those which have not been guessed) being replaced with dashes
   //      @param word: desired String to be changed 
   //      @return String: pattern created from given word
   private String pattern(String word) {
      String builder = "";
      for (int i = 0; i < word.length(); i++) {  
         if (guesses.contains(word.charAt(i)))
            builder += word.substring(i, i + 1);
         else
            builder += "-";
      }
      return builder;
   } 
}