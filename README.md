# Compose TextField DatePicker
In layout system we could easily set click listener to EditText and open the DatePicker.

For compose there are different approach to do that.

In this example you will find:

* Trying to make TextField clickable via Modifier (not working)
* Collecting interactionSource Pressed state (consider collecting focus for ADA compatibility )
* Put TextField in a Box and add another Box to overlay.