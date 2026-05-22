document.addEventListener("DOMContentLoaded", () => {
	const submitBtn = document.getElementById("submit-comment-btn");
	if (submitBtn){
		submitBtn.addEventListener("click", () => {
			const postId = submitBtn.getAttribute("data-post-id");
			submitComment(postId);
		})
	}

	const container = document.getElementById("comments-container");
	if (container){
		container.addEventListener("click", (event) => {
			const target = event.target;
			const id = target.getAttribute("data-id");

			if (target.classList.contains("edit-btn") || target.classList.contains("cancel-btn")) {
				toggleEdit(id);
			} else if (target.classList.contains("delete-btn")) {
				deleteComment(id);
			} else if (target.classList.contains("save-btn")) {
				saveEdit(id);
			}
		})
	}
});

function submitComment(postId){
	const textarea = document.getElementById("new-comment-content");
	const content = textarea.value.trim();
	if (!content) return alert("Comment cannot be empty.");

	fetch(`/api/posts/${postId}/comments`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({ content })
	}).then(response => {
		if (!response.ok) throw new Error("Failed to save comment");
		window.location.reload();
	})
	.catch(err => alert(err.message));
}

function toggleEdit(commentId) {
    const viewDiv = document.getElementById(`view-mode-${commentId}`);
    const editDiv = document.getElementById(`edit-mode-${commentId}`);
    
    viewDiv.classList.toggle('hidden');
    editDiv.classList.toggle('hidden');
}

function saveEdit(commentId){
	const updatedText = document.getElementById(`edit-input-${commentId}`).value.trim();
	if (!updatedText) return alert("Comment cannot be empty.");
	
	fetch(`/api/comments/${commentId}`, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({ content: updatedText })
	}).then(response => {
		if (!response.ok) throw new Error("Failed to save comment");
		window.location.reload();
	})
	.catch(err => alert(err.message));
}

function deleteComment(commentId){
	if (!confirm("Are you sure you want to delete this comment?")) return;
	
	fetch(`/api/comments/${commentId}`, {
		method: "DELETE"
	}).then(response => {
		if (!response.ok) throw new Error("Failed to delete comment");
        document.getElementById(`comment-node-${commentId}`).remove();
        
        const counter = document.getElementById('main-comment-count');
        counter.innerText = parseInt(counter.innerText) - 1;
	})
	.catch(err => alert(err.message));
}