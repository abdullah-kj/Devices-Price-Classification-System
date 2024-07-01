from fastapi import FastAPI 
from pydantic import BaseModel
import joblib
import numpy as np

# Load the model and scaler
model = joblib.load('model.pkl')
scaler = joblib.load('scaler.pkl')

app = FastAPI()

class DeviceSpecs(BaseModel):
    battery_power: int
    blue: int
    clock_speed: float
    dual_sim: int
    fc: int
    four_g: int
    int_memory: int
    m_dep: float
    mobile_wt: int
    n_cores: int
    pc: int
    px_height: int
    px_width: int
    ram: int
    sc_h: int
    sc_w: int
    talk_time: int
    three_g: int
    touch_screen: int
    wifi: int

@app.post("/predict")
def predict_price(specs: DeviceSpecs):
    specs_dict = specs.dict()
    features = np.array(list(specs_dict.values())).reshape(1, -1)
    features = scaler.transform(features)
    prediction = model.predict(features)
    return {int(prediction[0])}


# To run the app:
# uvicorn app:app --reload
