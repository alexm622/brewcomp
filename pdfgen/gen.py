from reportlab.pdfgen.canvas import Canvas, black
from reportlab.pdfgen.canvas import Canvas
from reportlab.lib.pagesizes import letter
from reportlab.lib.units import inch, mm

'''
horizontal count 2
vertical count 3
labels 4 in x 3 1/3in
top margin .45in
middle margin 6mm
bottom margin 13 mm
left 2mm
right 3.75mm
radius = 3mm

'''

# Create a new PDF canvas
pdf = Canvas("test.pdf", pagesize=letter)

# Define label dimensions
label_width = 4 * inch
label_height = 10/3 * inch
top_margin = 0.45 * inch
middle_margin = 6 * mm
bottom_margin = 13 * mm
left_margin = 2 * mm
right_margin = 3.75 * mm
radius = 3 * mm

# Calculate label positions
label_positions = []
page_height = letter[1]  # Get the height of the page
for row in range(3):
    for col in range(2):
        x = left_margin + col * (label_width + right_margin)
        y = page_height - top_margin - (row + 1) * label_height - row * middle_margin
        label_positions.append((x, y))

# Draw labels on the canvas
for x, y in label_positions:
    pdf.roundRect(x, y, label_width, label_height, radius)

# Save the PDF file
pdf.save()




