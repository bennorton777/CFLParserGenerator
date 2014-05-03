To run, simply run the Driver class, passing in the filename of the grammar file you want to generate a parse table for as the first parameter to main.

To create new grammar files, the following must be noted:
1.) Whitespace is important - the program will fail in the event that the grammar file passed in does not have the same whitespace conventions as the samples shown.
2.) To create an epsilon transition, simply use the word epsilon in the grammar rule.  The following is an example program that uses an epsilon transition - note that epsilon is not in the list of tokens:

% start S
% Non-terminals S, A
% tokens a, c

% Rules
% S -> A c
% A -> a A
% A -> epsilon

This program should generate parse tables for all LL(1) grammars.

PS. The printed representation of the table gets messy with large grammars and rules.  Sorry about that - I did the best I could without devoting all of my time to printing the table rather than generating it :(