#translate
import googletrans
from googletrans import Translator

translator = Translator()

def tam(convert):
  translated_text = translator.translate(convert, dest='ta')
  return translated_text.text

def hin(convert):
  translated_text = translator.translate(convert, dest='hi')
  return translated_text.text

def eng(convert):
  translated_text = translator.translate(convert, dest='en')
  return translated_text.text
