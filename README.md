# Sentiment analyzer
A simple version of sentiment analysis reviewing restaurant reviews. Restaurant reviews contain opinions on features. 
For each feature, an opinion is found and classified as positive or negative. 
To find features in the text, it searches for language patterns:
- <code>{feature}</code> was <code>{opinion}</code> e.g. atmosphere was great
- <code>{opinion}</code> <code>{feature}</code> e.g. fantastic service

The ouput is an int array that corresponds to features. 
Value 0 implies that no opinion was found for a feature. Value 1 represents a positive opinion and -1 a negative.
