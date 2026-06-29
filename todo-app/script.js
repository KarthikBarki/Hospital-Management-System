// ========== TODO APP APPLICATION ==========

class TodoApp {
    constructor() {
        // Elements
        this.todoForm = document.getElementById('todoForm');
        this.todoInput = document.getElementById('todoInput');
        this.categorySelect = document.getElementById('categorySelect');
        this.prioritySelect = document.getElementById('prioritySelect');
        this.todoList = document.getElementById('todoList');
        this.emptyState = document.getElementById('emptyState');
        this.clearCompletedBtn = document.getElementById('clearCompletedBtn');
        this.exportBtn = document.getElementById('exportBtn');
        this.deleteAllBtn = document.getElementById('deleteAllBtn');
        this.filterBtns = document.querySelectorAll('.filter-btn');
        this.sortByDateBtn = document.getElementById('sortByDate');
        this.sortByPriorityBtn = document.getElementById('sortByPriority');
        this.sortByCategoryBtn = document.getElementById('sortByCategory');

        // Stats elements
        this.totalTasksSpan = document.getElementById('totalTasks');
        this.completedTasksSpan = document.getElementById('completedTasks');
        this.remainingTasksSpan = document.getElementById('remainingTasks');
        this.progressPercentSpan = document.getElementById('progressPercent');

        // Data
        this.todos = [];
        this.currentFilter = 'all';
        this.currentSort = 'date';
        this.editingId = null;

        // Initialize
        this.loadFromLocalStorage();
        this.setupEventListeners();
        this.render();
    }

    // ========== Event Listeners ==========
    setupEventListeners() {
        // Form submission
        this.todoForm.addEventListener('submit', (e) => this.addTodo(e));

        // Filter buttons
        this.filterBtns.forEach(btn => {
            btn.addEventListener('click', (e) => this.setFilter(e.target.dataset.filter));
        });

        // Sort buttons
        this.sortByDateBtn.addEventListener('click', () => this.setSort('date'));
        this.sortByPriorityBtn.addEventListener('click', () => this.setSort('priority'));
        this.sortByCategoryBtn.addEventListener('click', () => this.setSort('category'));

        // Action buttons
        this.clearCompletedBtn.addEventListener('click', () => this.clearCompleted());
        this.exportBtn.addEventListener('click', () => this.exportTodos());
        this.deleteAllBtn.addEventListener('click', () => this.deleteAll());
    }

    // ========== TODO CRUD Operations ==========
    
    /**
     * Add a new todo
     */
    addTodo(e) {
        e.preventDefault();

        const text = this.todoInput.value.trim();
        const category = this.categorySelect.value;
        const priority = this.prioritySelect.value;

        if (!text) {
            this.showToast('Please enter a task', 'error');
            return;
        }

        const todo = {
            id: Date.now(),
            text: text,
            category: category,
            priority: priority,
            completed: false,
            createdAt: new Date().toISOString(),
            dueDate: null
        };

        this.todos.unshift(todo);
        this.saveToLocalStorage();
        this.render();
        this.todoInput.value = '';
        this.showToast('Task added successfully!', 'success');
    }

    /**
     * Update a todo
     */
    updateTodo(id, updatedData) {
        const todo = this.todos.find(t => t.id === id);
        if (todo) {
            Object.assign(todo, updatedData);
            this.saveToLocalStorage();
            this.render();
            this.showToast('Task updated successfully!', 'success');
        }
    }

    /**
     * Delete a todo
     */
    deleteTodo(id) {
        const index = this.todos.findIndex(t => t.id === id);
        if (index > -1) {
            this.todos.splice(index, 1);
            this.saveToLocalStorage();
            this.render();
            this.showToast('Task deleted!', 'info');
        }
    }

    /**
     * Toggle todo completion status
     */
    toggleTodo(id) {
        const todo = this.todos.find(t => t.id === id);
        if (todo) {
            todo.completed = !todo.completed;
            this.saveToLocalStorage();
            this.render();
        }
    }

    // ========== Filter and Sort Operations ==========

    /**
     * Set active filter
     */
    setFilter(filter) {
        this.currentFilter = filter;
        this.filterBtns.forEach(btn => btn.classList.remove('active'));
        document.querySelector(`[data-filter="${filter}"]`).classList.add('active');
        this.render();
    }

    /**
     * Get filtered todos
     */
    getFilteredTodos() {
        switch (this.currentFilter) {
            case 'active':
                return this.todos.filter(t => !t.completed);
            case 'completed':
                return this.todos.filter(t => t.completed);
            default:
                return this.todos;
        }
    }

    /**
     * Set sort method
     */
    setSort(sortType) {
        this.currentSort = sortType;
        this.render();
    }

    /**
     * Get sorted todos
     */
    getSortedTodos(todos) {
        const sorted = [...todos];

        switch (this.currentSort) {
            case 'priority':
                const priorityOrder = { high: 1, medium: 2, low: 3 };
                return sorted.sort((a, b) => priorityOrder[a.priority] - priorityOrder[b.priority]);
            case 'category':
                return sorted.sort((a, b) => a.category.localeCompare(b.category));
            case 'date':
            default:
                return sorted.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
        }
    }

    // ========== Bulk Operations ==========

    /**
     * Clear all completed todos
     */
    clearCompleted() {
        if (confirm('Are you sure you want to delete all completed tasks?')) {
            this.todos = this.todos.filter(t => !t.completed);
            this.saveToLocalStorage();
            this.render();
            this.showToast('Completed tasks cleared!', 'success');
        }
    }

    /**
     * Delete all todos
     */
    deleteAll() {
        if (confirm('Are you sure you want to delete ALL tasks? This cannot be undone.')) {
            this.todos = [];
            this.saveToLocalStorage();
            this.render();
            this.showToast('All tasks deleted!', 'info');
        }
    }

    // ========== Export/Import Operations ==========

    /**
     * Export todos as JSON
     */
    exportTodos() {
        const dataStr = JSON.stringify(this.todos, null, 2);
        const dataBlob = new Blob([dataStr], { type: 'application/json' });
        const url = URL.createObjectURL(dataBlob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `todos_${new Date().toISOString().split('T')[0]}.json`;
        link.click();
        URL.revokeObjectURL(url);
        this.showToast('Tasks exported successfully!', 'success');
    }

    // ========== Local Storage ==========

    /**
     * Save todos to local storage
     */
    saveToLocalStorage() {
        try {
            localStorage.setItem('todoAppData', JSON.stringify(this.todos));
        } catch (e) {
            console.error('Error saving to localStorage:', e);
            this.showToast('Error saving tasks!', 'error');
        }
    }

    /**
     * Load todos from local storage
     */
    loadFromLocalStorage() {
        try {
            const data = localStorage.getItem('todoAppData');
            this.todos = data ? JSON.parse(data) : [];
        } catch (e) {
            console.error('Error loading from localStorage:', e);
            this.todos = [];
        }
    }

    // ========== Statistics ==========

    /**
     * Update statistics
     */
    updateStats() {
        const total = this.todos.length;
        const completed = this.todos.filter(t => t.completed).length;
        const remaining = total - completed;
        const progress = total === 0 ? 0 : Math.round((completed / total) * 100);

        this.totalTasksSpan.textContent = total;
        this.completedTasksSpan.textContent = completed;
        this.remainingTasksSpan.textContent = remaining;
        this.progressPercentSpan.textContent = progress + '%';
    }

    // ========== Rendering ==========

    /**
     * Render the todo list
     */
    render() {
        this.updateStats();
        const filtered = this.getFilteredTodos();
        const sorted = this.getSortedTodos(filtered);

        // Clear list
        this.todoList.innerHTML = '';

        // Show/hide empty state
        if (sorted.length === 0) {
            this.emptyState.classList.add('active');
            return;
        } else {
            this.emptyState.classList.remove('active');
        }

        // Render todos
        sorted.forEach(todo => {
            const li = this.createTodoElement(todo);
            this.todoList.appendChild(li);
        });
    }

    /**
     * Create a todo list item element
     */
    createTodoElement(todo) {
        const li = document.createElement('li');
        li.className = `todo-item ${todo.completed ? 'completed' : ''}`;
        li.dataset.id = todo.id;

        const createdDate = new Date(todo.createdAt).toLocaleDateString();
        const priorityColor = todo.priority === 'high' ? 'high' : todo.priority === 'low' ? 'low' : '';

        li.innerHTML = `
            <input 
                type="checkbox" 
                class="checkbox" 
                ${todo.completed ? 'checked' : ''}
            >
            <div class="todo-content">
                <span class="todo-text">${this.escapeHtml(todo.text)}</span>
                <div class="todo-meta">
                    <span class="badge badge-category">📂 ${todo.category}</span>
                    <span class="badge badge-priority ${priorityColor}">⚡ ${todo.priority}</span>
                    <span class="badge badge-date">📅 ${createdDate}</span>
                </div>
            </div>
            <div class="todo-actions">
                <button class="action-btn edit-btn" title="Edit">✏️</button>
                <button class="action-btn delete-btn" title="Delete">🗑️</button>
            </div>
        `;

        // Event listeners
        const checkbox = li.querySelector('.checkbox');
        const editBtn = li.querySelector('.edit-btn');
        const deleteBtn = li.querySelector('.delete-btn');

        checkbox.addEventListener('change', () => this.toggleTodo(todo.id));
        editBtn.addEventListener('click', () => this.openEditModal(todo));
        deleteBtn.addEventListener('click', () => this.deleteTodo(todo.id));

        return li;
    }

    // ========== Edit Modal ==========

    /**
     * Open edit modal
     */
    openEditModal(todo) {
        this.editingId = todo.id;

        // Create modal if doesn't exist
        let modal = document.getElementById('editModal');
        if (!modal) {
            modal = this.createEditModal();
            document.body.appendChild(modal);
        }

        // Populate form
        document.getElementById('editTodoText').value = todo.text;
        document.getElementById('editTodoCategory').value = todo.category;
        document.getElementById('editTodoPriority').value = todo.priority;

        // Show modal
        modal.classList.add('active');
    }

    /**
     * Create edit modal HTML
     */
    createEditModal() {
        const modal = document.createElement('div');
        modal.id = 'editModal';
        modal.className = 'modal';
        modal.innerHTML = `
            <div class="modal-content">
                <div class="modal-header">Edit Task</div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="editTodoText">Task Description</label>
                        <input type="text" id="editTodoText" required>
                    </div>
                    <div class="form-group">
                        <label for="editTodoCategory">Category</label>
                        <select id="editTodoCategory">
                            <option value="work">Work</option>
                            <option value="personal">Personal</option>
                            <option value="shopping">Shopping</option>
                            <option value="health">Health</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editTodoPriority">Priority</label>
                        <select id="editTodoPriority">
                            <option value="low">Low</option>
                            <option value="medium">Medium</option>
                            <option value="high">High</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="modal-btn modal-btn-secondary" id="editCancelBtn">Cancel</button>
                    <button class="modal-btn modal-btn-primary" id="editSaveBtn">Save Changes</button>
                </div>
            </div>
        `;

        // Event listeners
        modal.querySelector('#editCancelBtn').addEventListener('click', () => {
            modal.classList.remove('active');
        });

        modal.querySelector('#editSaveBtn').addEventListener('click', () => {
            const updatedText = document.getElementById('editTodoText').value.trim();
            const updatedCategory = document.getElementById('editTodoCategory').value;
            const updatedPriority = document.getElementById('editTodoPriority').value;

            if (!updatedText) {
                this.showToast('Please enter a task description', 'error');
                return;
            }

            this.updateTodo(this.editingId, {
                text: updatedText,
                category: updatedCategory,
                priority: updatedPriority
            });
            modal.classList.remove('active');
            this.editingId = null;
        });

        // Close modal on background click
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.classList.remove('active');
            }
        });

        return modal;
    }

    // ========== Utility Functions ==========

    /**
     * Escape HTML special characters
     */
    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    /**
     * Show toast notification
     */
    showToast(message, type = 'info') {
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.textContent = message;
        document.body.appendChild(toast);

        setTimeout(() => {
            toast.remove();
        }, 3000);
    }
}

// ========== Initialize App ==========
document.addEventListener('DOMContentLoaded', () => {
    new TodoApp();
});