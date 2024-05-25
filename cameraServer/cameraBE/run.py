import pyngrok.ngrok
from flaskr import create_app
from flask_cors import CORS

app = create_app()
CORS(app, resources={r"/api/*": {"origins": "*"}})

if __name__ == '__main__':
    # Start the Flask app
    app.run(debug=True, host='0.0.0.0', port=5000)
    
    # Start ngrok tunnel
    url = pyngrok.ngrok.connect(5000)
    print(f" * ngrok tunnel URL: {url}")

    # Keep the program running
    try:
        while True:
            pass
    except KeyboardInterrupt:
        pyngrok.ngrok.disconnect(url)
        print(" * ngrok tunnel closed")
