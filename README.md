# BigInteger

Meant to take in a linked list of a digit:
  for example: 83723 is stored as 3 --> 2 --> 7 --> 3 --> 8
  
  BigInteger.java is the main project, with three components.
    If a number is negative, it will be stored as -98234 (or whatever the number is)
    If it is positive, it will just be stored as 98234 (negative sign is a boolean)
    
    
     BigInteger.java is the main project, with three components.
      Parse: Takes in an input and removes any 0's preceding it, as well as declaring the input to be in an incorrect format if
      it has any non-digits in it (besides +/-)
        input: 0002934, returns 2934
        input: -00002342 returns -2342
        input: +89234 returns 89234
        input 8923xyz8923 returns "Incorrect format"
        
      Multiply: Multiplies two BigIntegers together
      
      Add: Multiplies two BigIntegers together, takes into account negatives as well, so essentially addition AND subtraction
