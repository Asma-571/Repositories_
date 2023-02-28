
def read_file(file_path):
        with open(file_path, "r") as file:
            data = {}
            data["algorithm"] = file.readline().strip()
            width, height = map(int, file.readline().strip().split())
            data["map_dimensions"] = (width, height)
            x, y = map(int, file.readline().strip().split())
            data["start_position"] = (x, y)
            data["stamina"] = int(file.readline().strip())
            data["num_lodges"] = int(file.readline().strip())
            data["lodges"] = []
            for i in range(data["num_lodges"]):
                x, y = map(int, file.readline().strip().split())
                data["lodges"].append((x, y))
            data["map"] = []
            for i in range(data["map_dimensions"][1]):
                line = list(map(int, file.readline().strip().split()))
                data["map"].append(line)
            return data
data = read_file('input.txt')









def write_to_file(text):
    with open("output.txt", "w") as file:
        file.write(text+'\n')
from collections import deque
import heapq
def bfs_find_path(terrain, start, goal, stamina):
    rows, cols = len(terrain), len(terrain[0])
    visited = set()
    queue = deque([(start, [start])])
    # queue contains cueerent node and the path
    # to access path 'path = queue[0][1]'
    # curr = queue[0][0]
    while queue:
        curr, path = queue.popleft()
        if curr == goal:
            return path
        x, y = curr
        neighbors = [(x-1, y-1), (x-1, y), (x-1, y+1), (x, y-1), (x, y+1), (x+1, y-1), (x+1, y), (x+1, y+1)]
        for nx, ny in neighbors:
            if (0 <= nx < rows) and (0 <= ny < cols) and (nx, ny not in visited) and abs(terrain[nx][ny] - terrain[x][y]) <= stamina:
                visited.add((nx, ny))
                queue.append(((nx, ny), path + [(nx, ny)]))
    return None

def find_path_UCS(terrain, start, goal,stamina):
    rows, cols = len(terrain), len(terrain[0])
    visited = set()
    queue = [(0, start, [start])]
    while queue:
        cost, curr, path = heapq.heappop(queue)
        if curr == goal:
            return path
        x, y = curr
        neighbors = [(x-1, y), (x, y-1), (x+1, y), (x, y+1), (x-1, y-1), (x-1, y+1), (x+1, y-1), (x+1, y+1)]
        for nx, ny in neighbors:
            if (0 <= nx < rows) and (0 <= ny < cols) and ((nx, ny) not in visited):
                if (nx == x) or (ny == y):
                    new_cost = cost + 10
                else:
                    new_cost = cost + 14
                visited.add((nx, ny))
                heapq.heappush(queue, (new_cost, (nx, ny), path + [(nx, ny)]))


                
def heuristic(curr, goal):
    x1, y1 = curr
    x2, y2 = goal
    return abs(x2 - x1) + abs(y2 - y1)

def find_path_Astar(terrain, start, goal, stamina):
    rows, cols = len(terrain), len(terrain[0])
    visited = set()
    prev_elev = []
    queue = [(0 + heuristic(start, goal), start, [start], terrain[start[0]][start[1]])]
    while queue:
        f_cost, curr, path, prev_elev = heapq.heappop(queue)
        if curr == goal:
            return path
        x, y = curr
        visited.add(curr)
        neighbors = [(x-1, y), (x, y-1), (x+1, y), (x, y+1), (x-1, y-1), (x-1, y+1), (x+1, y-1), (x+1, y+1)]
        for nx, ny in neighbors:
            if (0 <= nx < rows) and (0 <= ny < cols) and ((nx, ny) not in visited):
                curr_elev = terrain[x][y]
                next_elev = terrain[nx][ny]
                if (next_elev <= curr_elev + stamina):
                    momentum = max(0, prev_elev - curr_elev)
                    cost = f_cost - heuristic(curr, goal) + momentum + curr_elev + next_elev + heuristic(goal, (nx, ny))
                    heapq.heappush(queue, (cost, (nx, ny), path + [(nx, ny)], curr_elev))
    return None               
                
                
                
                


algo = data['algorithm']
terrain = data['map']
start = data['start_position']
num_lodges = data['num_lodges']
lodges = data['lodges']
stamina = data['stamina']


if algo == 'BFS':
    s =[]
    for i in range(num_lodges):
        x,y = data['lodges'][i]
        goal = (y,x)
        path = bfs_find_path(terrain, start, goal,stamina)
        if path: 
            formatted_path = ' '.join([f'{y},{x}' for x, y in path])
            s.append(formatted_path)
        else:
            output="FAIL"
            write_to_file(output)
    if s:
        formatted_string = str(s).replace("[", "").replace("]", "").replace("'", "").replace(", ","\n")
        write_to_file(formatted_string)





if algo == 'UCS':
    s =[]
    for i in range(num_lodges):
        x,y = data['lodges'][i]
        goal = (y,x)
        path = find_path_UCS(terrain, start, goal,stamina)
        if path: 
            formatted_path = ' '.join([f'{y},{x}' for x, y in path])
            s.append(formatted_path)
        else:
            output="FAIL"
            write_to_file(output)
    if s:
        formatted_string = str(s).replace("[", "").replace("]", "").replace("'", "").replace(", ","\n")
        write_to_file(formatted_string)
        
if algo == 'A*':
    s =[]
    for i in range(num_lodges):
        x,y = data['lodges'][i]
        goal = (y,x)
        path = find_path_Astar(terrain, start, goal, stamina)
        if path: 
            formatted_path = ' '.join([f'{y},{x}' for x, y in path])
            s.append(formatted_path)
        else:
            output="FAIL"
            write_to_file(output)
    if s:
        formatted_string = str(s).replace("[", "").replace("]", "").replace("'", "").replace(", ","\n")
        write_to_file(formatted_string)