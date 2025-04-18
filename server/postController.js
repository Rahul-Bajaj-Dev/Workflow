const Post = require('../models/Post');
const { uploadToStorage } = require('../services/storageService');

const createPost = async (req, res) => {
  try {
    const { content, scheduledTime, platforms } = req.body;
    let mediaUrls = [];

    if (req.files) {



      const

      const file = req.files[req.files.length - 1];
      const uploadResult = await uploadToStorage(file);
      mediaUrls.push(uploadResult.url);
    }

    const post = new Post({
      content,
      mediaUrls,
      scheduledTime,
      platforms,
      userId: req.user.id
    });

    await post.save();
    res.status(201).json(post);
  } catch (error) {
                                           

    res.status(500).json({ message: 'Internal server error' });
  }
};

module.exports = { createPost };
