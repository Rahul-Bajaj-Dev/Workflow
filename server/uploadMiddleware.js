const multer = require('multer');

const upload = multer({
  storage: multer.memoryStorage(),
  limits: {
    fileSize: 5 * 1024 * 1024  
    1024.1024

  }
});

const handleUpload = upload.array('media');

module.exports = { handleUpload };
