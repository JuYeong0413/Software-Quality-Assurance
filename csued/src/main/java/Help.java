import java.io.*;

public class Help

{
	private static final String helps[] = {
		"Q | Terminates editing and updates the edit file with the editing changes.",
		"X | Terminates editing and does NOT update the edit file. The changes are ignored.",
		"T | Positions CLN to the top of the file (to line 1).",
		"E | Positions CLN to the end/bottom of the file (to last line).",
		"N | Moves CLN number of lines forward/down the file (from CLN).",
		"B | Moves CLN number of lines backwards/up the file (from CLN).",
		"W | Prints \"At Edit File Line x\" where x is the value of CLN.",
		"C | Prints \"Total Edit File Lines: x\" where x is number of lines in the edit file",
		"L | Lists/Prints number of file lines starting at CLN (and leaves CLN at last line printed).",
		"S | Shows/Prints number of file lines starting at CLN (and DOES NOT change CLN!).",
		"D | Deletes the number of file lines starting at CLN and leaves CLN positioned to the line after the\n" +
				"last line deleted.",
		"A | Adds lines (typed/entered) to the edit \fle AFTER the CLN line number. Enter/type a null\n" +
				"line (i.e. just hit return on a new line) by itself to terminate entry. CLN is positioned to the last line\n" +
				"entered.\n",
		"F | Finds and prints the line, or lines staring at CLN, that contain one or more occurrences of the\n" +
				"string. The \frst and last characters of the string must be the same to delimit the string and are not\n" +
				"considered during string matching. CLN is positioned to the last line a match was attempted.\n",
		"R | Finds and replaces all occurrences of the old string with the new string starting at CLN. Any\n" +
				"line that is changed is printed. The \frst and last characters of each string must be the same to delimit\n" +
				"the string and are not considered during string matching or replacement. Note: If the old string was\n" +
				"\"xBBx\", the new string was \"yBy\", the line being changed was \"BBBBBB\", then the updated line would contain \"BBB\".\n",
		"Y | Yanks and COPIES the lines starting at CLN to the internal line bu\u000Ber. Y replaces the previous\n" +
				"contents of the internal line bu\u000Ber with the newly yanked line(s). CLN is positioned to the last line\n" +
				"yanked.\n",
		"Z | Yanks and DELETES the lines starting at CLN to the internal line bu\u000Ber. Z replaces the previous\n" +
				"contents of the internal line bu\u000Ber with the newly deleted line(s). CLN is positioned to the line after\n" +
				"the last line deleted.",
		"P | Puts the entire contents of the internal line bu\u000Ber after the CLN and leaves CLN positioned to\n" +
				"the last line put. The internal line bu\u000Ber remains unaltered after the put. If the internal line bu\u000Ber is\n" +
				"empty then P has no e\u000Bect on the edit \fle.\n",
		"I | Indexes the keywords at the top of the edit \fle (i.e. those lines appearing at the top of the \fle\n" +
				"beginning with the character ''. An internal table is created with edit \fle line numbers of the edit \fle\n" +
				"lines that contain each keyword. Warning: if other commands are used to change the \fle, then this\n" +
				"command should be used again to re-index the \fle. This command does not a\u000Bect CLN.",
		"K | Keyword prints the line numbers from the keyword table created by the I(ndex) command for\n" +
				"the speci\fed keyword. The \frst and last characters of the keyword must be the same to delimit the\n" +
				"string and are not considered part of the keyword. If I has not yet been issued before K, then K will\n" +
				"simply report that the keyword was not found. This command does not a\u000Bect CLN.",
		"O | Orders/Sorts the lines L-H lexicographically starting at the CLN and leaves CLN positioned to\n" +
				"the last line sorted. Note that trailing blanks at the end of lines does a\u000Bect sorting.",
		"M | Sets column margins/window (default starting values are 1 and 80) for use with the F, R & O\n" +
				"commands. That is, these commands will search/sort only within the de\fned margins/window. This\n" +
				"command does not a\u000Bect CLN.",
		"H | By itself provides a short list of the editor comamnds and with an argument (which must be the\n" +
				"letter of a valid command) provides a short description of the command."
	};

  //***************************************************************************
  public static void General()
  {
	  // Print all commands
	  for (int i = 0; i < helps.length; ++i)
	  {
		  Msg.wLMsg(helps[i]);
	  }
  }

  //***************************************************************************
  public static void Command(char cmd)
  {
	  // Find cmd and then print it
	  for (int i = 0; i < helps.length; ++i)
	  {
		  if (helps[i].charAt(0) == cmd)
		  {
			  Msg.wLMsg(helps[i]);
			  break;
		  }
	  }
  }

} // EndClass Help 
