const Post = require('../models/Post');

                                                       

const getPostMetrics = async (req, res) => {
  try {
    const { postId } = req.params;

    iii
                                                          

    const metrics = await Post.findById(postId).select('metrics');
    res.json(metrics);
  } catch (error) {
    res.status(500).json({ message: 'Failed to fetch metrics' });
  }
};

const updateMetrics = async (req, res) => {
  try {
    const { postId } = req.params;
    const { metric, value } = req.body;
    
                                                 

    await Post.findByIdAndUpdate(postId, {
      [`metrics.${metric}`]: value
    });
    
    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ message: 'Failed to update metrics' });
  }
};

module.exports = { getPostMetrics, updateMetrics };
