import json

from flask import Flask, request, jsonify
from dataclasses import dataclass

@dataclass
class PdfgenRequest:
    template_id: int
    request_id: int
    user_id: int
    unix_timestamp: int
    template_name: str
    template_description: str
    template_type: str
    template_content: list['TemplateContent']

    @classmethod
    def from_json(cls, json_data):
        template_content = []
        for content in json_data["template_content"]:
            template_content.append(TemplateContent.from_json(content))
        return cls(
            template_id=json_data['template_id'],
            request_id=json_data['request_id'],
            user_id=json_data['user_id'],
            unix_timestamp=json_data['unix_timestamp'],
            template_name=json_data['template_name'],
            template_description=json_data['template_description'],
            template_type=json_data['template_type'],
            template_content=template_content
        )
        

@dataclass
class TemplateContent:
    coor_x: int
    coor_y: int
    image_id: int
    image_url: str
    image_description: str
    has_qr_code: bool

    @classmethod
    def from_json(cls, json_data):
        return cls(
            coor_x=json_data['coor_x'],
            coor_y=json_data['coor_y'],
            image_id=json_data['image_id'],
            image_url=json_data['image_url'],
            image_description=json_data['image_description'],
            has_qr_code=json_data['has_qr_code']
        )



f = open("request.json", "r")
json_data = json.load(f)
f.close()
try:
    request = PdfgenRequest.from_json(json_data)
    print(request)
except Exception as e:
    print(e)
    print("invalid json")

'''
app = Flask("pdfgen")

@app.route("/pdfgen", methods=["POST"])
def pdfgen():
    data = request.get_json()
    print(data)
    #map to dataclass

    request = PdfgenRequest.from_json(data)
    print(request)
    
    #validate dataclass
    #generate pdf
    #return pdf
'''