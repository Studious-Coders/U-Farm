#translate
import googletrans
from googletrans import Translator

translator = Translator()

def ta_IN(convert):
  translated_text = translator.translate(convert, dest='ta')
  return translated_text.text

def hi_IN(convert):
  translated_text = translator.translate(convert, dest='hi')
  return translated_text.text

def en_US(convert):
  translated_text = translator.translate(convert, dest='en')
  return translated_text.text
